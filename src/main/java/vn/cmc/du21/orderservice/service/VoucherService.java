package vn.cmc.du21.orderservice.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherUserRepository;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUserId;
import vn.cmc.du21.orderservice.common.DateTimeUtil;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherUserResponse;

import java.util.ArrayList;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherUserRepository voucherUserRepository;

    @Transactional
    public List<Voucher> applyVoucher(List<Voucher> listVoucher, long totalPrice, long userId) {
        List<Voucher> newListVoucher = new ArrayList<>();

        for(Voucher item : listVoucher)
        {
            Voucher foundVoucher = voucherRepository.findAvailableVoucher(item.getCodeVoucher(), totalPrice, userId)
                    .orElse(null);
            if(foundVoucher != null)
            {
                newListVoucher.add(foundVoucher);
            }
        }

        return newListVoucher;
    }

    @Transactional
    public Voucher getDetailVoucherById(long voucherId) throws Throwable{
        return voucherRepository.findById(voucherId).orElseThrow(()->{
            throw new RuntimeException(("The voucher was not found"));
        });
    }

    @Transactional
    public void saveVoucher(long userId, String codeVoucher) throws Throwable{
        Optional<Voucher> voucher =voucherRepository.findByCodeVoucher(codeVoucher);
        if(voucher.isPresent()){
            Optional<VoucherUser> voucherUsers = voucherUserRepository
                    .findByVoucherIdAndUserId(voucher.get().getVoucherId(), userId);
            if (!voucherUsers.isPresent()) {
                VoucherUserId voucherUserId = new VoucherUserId();
                voucherUserId.setVoucherId(voucher.get().getVoucherId());
                voucherUserId.setUserId(userId);
                VoucherUser voucherUser = new VoucherUser();
                voucherUser.setVoucherUserId(voucherUserId);
                voucherUser.setUsedTimes(0);
                voucherUser.setVoucher(voucher.get());
                voucherUserRepository.save(voucherUser);
            }else {
                throw new RuntimeException("The voucher already on!! ");
            }
        }else {
            throw new RuntimeException("Not found");
        }
    }

    @Transactional
    public List<VoucherUser> getMyVoucher(long userId,@NotNull String status) throws Throwable{
                    List<VoucherUser> voucherUsers = new ArrayList<>();
                    if(status.equals("available")) {
                        for (VoucherUser item : voucherUserRepository.findAllByVoucherUser_UserId(userId)) {
                            VoucherUser voucherUser = new VoucherUser();
                            Voucher voucher = new Voucher();
                            voucher.setVoucherId(item.getVoucher().getVoucherId());
                            voucher.setCodeVoucher(item.getVoucher().getCodeVoucher());
                            //compare startTime, endTime to current time, quantity > 0, time of use > usedTime
                            if (item.getVoucher().getStartTime().compareTo(DateTimeUtil.getTimeNow()) < 0
                                    && item.getVoucher().getEndTime().compareTo(DateTimeUtil.getTimeNow()) > 0
                                    && item.getVoucher().getQuantity() > 0
                                    && item.getVoucher().getTimesOfUse() > item.getUsedTimes()) {
                                voucher.setStartTime(item.getVoucher().getStartTime());
                                voucher.setEndTime(item.getVoucher().getEndTime());
                                voucher.setQuantity(item.getVoucher().getQuantity());
                                voucher.setTimesOfUse(item.getVoucher().getTimesOfUse());
                                voucher.setImage(item.getVoucher().getImage());
                                voucher.setTitle(item.getVoucher().getTitle());
                                voucher.setPercentValue(item.getVoucher().getPercentValue());
                                voucher.setUpToValue(item.getVoucher().getUpToValue());
                                voucher.setApplicableValue(item.getVoucher().getApplicableValue());
                                //set Voucher User Response
                                voucherUser.setVoucher(voucher);
                                voucherUser.setUsedTimes(item.getUsedTimes());
                                //------
                                voucherUsers.add(voucherUser);
                            }
                        }
                    }else {
                        for (VoucherUser item : voucherUserRepository.findAllByVoucherUser_UserId(userId)) {
                            VoucherUser voucherUser = new VoucherUser();
                            Voucher voucher = new Voucher();
                            voucher.setVoucherId(item.getVoucher().getVoucherId());
                            voucher.setCodeVoucher(item.getVoucher().getCodeVoucher());
                                voucher.setStartTime(item.getVoucher().getStartTime());
                                voucher.setEndTime(item.getVoucher().getEndTime());
                                voucher.setQuantity(item.getVoucher().getQuantity());
                                voucher.setTimesOfUse(item.getVoucher().getTimesOfUse());
                                voucher.setImage(item.getVoucher().getImage());
                                voucher.setTitle(item.getVoucher().getTitle());
                                voucher.setPercentValue(item.getVoucher().getPercentValue());
                                voucher.setUpToValue(item.getVoucher().getUpToValue());
                                voucher.setApplicableValue(item.getVoucher().getApplicableValue());
                                //set Voucher User Response
                                voucherUser.setVoucher(voucher);
                                voucherUser.setUsedTimes(item.getUsedTimes());
                                //------
                                voucherUsers.add(voucherUser);

                        }
                    }


        if(voucherUsers.isEmpty()){
            throw new RuntimeException("There are was not voucher to display");
        }

        return voucherUsers;
    }


    @Transactional
    public List<Voucher> getListVoucher(long userId)throws Throwable{
        List<Voucher> vouchers = new ArrayList<>();

        for (Voucher item : voucherRepository.findAll()){
            Optional<VoucherUser> voucherUser = voucherUserRepository.findAllByVoucherUser_UserId_VoucherId(userId,item.getVoucherId());
            if(!voucherUser.isPresent()){

                //compare startTime, endTime to current time, quantity > 0,
                if(item.getStartTime().compareTo(DateTimeUtil.getTimeNow())<0 && item.getEndTime().compareTo(DateTimeUtil.getTimeNow())>0
                        && item.getQuantity()>0 )
                {
                    vouchers.add(item);
                }

            }

        }
        if(vouchers.isEmpty())
        {
            throw new RuntimeException("there are not voucher to display");
        }
        return vouchers;
    }
}
