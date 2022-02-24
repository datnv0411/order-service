package vn.cmc.du21.orderservice.service;

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
    public List<VoucherUserResponse> getMyVoucher(long userId) throws Throwable{
        List<VoucherUserResponse> voucherUserResponses = new ArrayList<>();

        for(VoucherUser item : voucherUserRepository.findAllByVoucherUser_UserId(userId))
        {
            VoucherUserResponse voucherUserResponse = new VoucherUserResponse();
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setVoucherId(item.getVoucher().getVoucherId());
            voucherResponse.setCodeVoucher(item.getVoucher().getCodeVoucher());

            //compare startTime, endTime to current time, quantity > 0, time of use > usedTime
            if(item.getVoucher().getStartTime().compareTo(DateTimeUtil.getTimeNow())<0
                    && item.getVoucher().getEndTime().compareTo(DateTimeUtil.getTimeNow())>0
                    && item.getVoucher().getQuantity()>0
                    && item.getVoucher().getTimesOfUse() > item.getUsedTimes()) {

                voucherResponse.setStartTime(DateTimeUtil.timestampToString(item.getVoucher().getStartTime()));
                voucherResponse.setEndTime(DateTimeUtil.timestampToString(item.getVoucher().getEndTime()));
                voucherResponse.setQuantity(item.getVoucher().getQuantity());
                voucherResponse.setTimesOfUse(item.getVoucher().getTimesOfUse());

                voucherResponse.setImage(item.getVoucher().getImage());
                voucherResponse.setTitle(item.getVoucher().getTitle());
                voucherResponse.setPercentValue(item.getVoucher().getPercentValue());
                voucherResponse.setUpToValue(item.getVoucher().getUpToValue());
                voucherResponse.setApplicableValue(item.getVoucher().getApplicableValue());
                //set Voucher User Response
                voucherUserResponse.setVoucherResponse(voucherResponse);
                voucherUserResponse.setUsedTimes(item.getUsedTimes());
                //------
                voucherUserResponses.add(voucherUserResponse);
            }
        }

        if(voucherUserResponses.isEmpty()){
            throw new RuntimeException("There are was not voucher to display");
        }

        return voucherUserResponses;
    }

    @Transactional
    public List<Voucher> getListVoucher(long userId)throws Throwable{
        List<Voucher> vouchers = new ArrayList<>();

        for (Voucher item : voucherRepository.findAll()){
            Optional<VoucherUser> voucherUser = voucherUserRepository.findAllByVoucherUser_UserId_VoucherId(userId,item.getVoucherId());
            if(voucherUser.isPresent()){
                continue;
            }else {

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
