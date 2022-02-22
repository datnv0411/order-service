package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherUserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherUserRepository voucherUserRepository;
    public List<Voucher> applyVoucher(List<Voucher> listVoucher, long totalPrice, long userId) {
        List<Voucher> newListVoucher = new ArrayList<>();

        for(Voucher item : listVoucher)
        {
            Voucher foundVoucher = checkAvailableVoucher(item.getCodeVoucher(), totalPrice, userId);
            if(foundVoucher != null)
            {
                newListVoucher.add(foundVoucher);
            }
        }

        return newListVoucher;
    }


    public Voucher checkAvailableVoucher(String codeVoucher, long totalPrice, long userId)
    {
        Optional<Voucher> foundVoucher = voucherRepository.findByCodeVoucher(codeVoucher);
        if(foundVoucher.isPresent())
        {
            if(foundVoucher.get().getEndTime().after(Timestamp.valueOf(LocalDateTime.now()))
                    && foundVoucher.get().getStartTime().before(Timestamp.valueOf(LocalDateTime.now()))
                    && foundVoucher.get().getQuantity() > 0)
            {
                Optional<VoucherUser> voucherUser = voucherUserRepository
                        .findByVoucherIdAndUserId(foundVoucher.get().getVoucherId(), userId);
                if(voucherUser.isPresent())
                {
                    if(voucherUser.get().getUsedTimes() < foundVoucher.get().getTimesOfUse()
                            && totalPrice >= foundVoucher.get().getApplicableValue())
                    {
                        return foundVoucher.get();
                    }
                }
            }
        }

        return null;
    }
}
