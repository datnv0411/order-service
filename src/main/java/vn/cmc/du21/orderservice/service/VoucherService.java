package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.common.DateTimeUtil;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUserId;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherUserRepository;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherUserResponse;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherUserRepository voucherUserRepository;

    @Transactional
    public Voucher getDetailVoucherById(long voucherId) throws Throwable{
        return voucherRepository.findById(voucherId).orElseThrow(()->{
            throw new RuntimeException(("The voucher was not found"));
        });
    }

    @Transactional
    public void SaveVoucher(long userId, long voucherId) throws Throwable{
        if(voucherRepository.findById(voucherId).isPresent()){
            VoucherUserId voucherUserId = new VoucherUserId();
            voucherUserId.setVoucherId(voucherId);
            voucherUserId.setUserId(userId);
            VoucherUser voucherUser = new VoucherUser();
            voucherUser.setVoucherUserId(voucherUserId);
            voucherUser.setUsedTimes(0);
            voucherUser.setVoucher(voucherRepository.findById(voucherId).get());
            voucherUserRepository.save(voucherUser);
        }else {
            throw new RuntimeException("Not found");
        }
    }

    @Transactional
    public List<VoucherUserResponse> getMyVoucher(long userId) throws Throwable{
        List<VoucherUserResponse> voucherUserResponses = new ArrayList<>();

    for(VoucherUser item : voucherUserRepository.findAllByVoucherUser_UserId(userId) )
    {
        VoucherUserResponse voucherUserResponse = new VoucherUserResponse();
        VoucherResponse voucherResponse = new VoucherResponse();
        voucherResponse.setVoucherId(item.getVoucher().getVoucherId());
        voucherResponse.setCodeVoucher(item.getVoucher().getCodeVoucher());
        //get current time
        Timestamp ts = Timestamp.from(Instant.now());
        //compare startTime, endTime to current time, quantity > 0, time of use > usedTime
        if(item.getVoucher().getStartTime().compareTo(ts)<0 && item.getVoucher().getEndTime().compareTo(ts)>0
                && item.getVoucher().getQuantity()>0 && item.getVoucher().getTimesOfUse() > item.getUsedTimes())
        {
            voucherResponse.setStartTime(DateTimeUtil.timestampToString(item.getVoucher().getStartTime()));
            voucherResponse.setEmdTime(DateTimeUtil.timestampToString(item.getVoucher().getEndTime()));
            voucherResponse.setQuantity(item.getVoucher().getQuantity());
            voucherResponse.setTimesOfUse(item.getVoucher().getTimesOfUse());
        }else {
             continue;
        }
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
                //get current time
                Timestamp ts = Timestamp.from(Instant.now());
                //compare startTime, endTime to current time, quantity > 0,
                if(item.getStartTime().compareTo(ts)<0 && item.getEndTime().compareTo(ts)>0
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
