package vn.cmc.du21.orderservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.orderservice.common.JwtTokenProvider;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.presentation.external.mapper.VoucherMapper;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;
import vn.cmc.du21.orderservice.service.VoucherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    VoucherService voucherService;

    @GetMapping("{voucherId}")
    StandardResponse<Object> getDetailVoucher(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable(name = "voucherId") long voucherId) throws Throwable
    {
        VoucherResponse voucherResponse = VoucherMapper.convertVouchertoVoucherResponse(voucherService.getDetailVoucherById(voucherId));
    return new StandardResponse<Object>(StatusResponse.SUCCESSFUL,"Found!!",voucherResponse);
    }

    @PostMapping("/save-voucher/{voucherId}")
    StandardResponse<Object> SaveVocher(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable(name = "voucherId") long voucherId) throws Throwable
    {
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        long userId = userLogin.getUserId();
//        long userId =1;
        voucherService.SaveVoucher(userId,voucherId);
        return new StandardResponse<Object>(StatusResponse.SUCCESSFUL,"Saved");
    }
    @GetMapping("/get-my-voucher")
    StandardResponse<Object> getMyVoucher(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
                UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);
                long userId = userLogin.getUserId();
//        long userId =1;
        return new StandardResponse<Object>(StatusResponse.SUCCESSFUL,"Found!!",voucherService.getMyVoucher(userId));
    }

    @GetMapping("/get-list-voucher")
    StandardResponse<Object> getListVoucher(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
                UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);
                long userId = userLogin.getUserId();
//        long userId =1;
        List<VoucherResponse> voucherResponses =voucherService.getListVoucher(userId).stream().map(VoucherMapper::convertVouchertoVoucherResponse).collect(Collectors.toList());
        return new StandardResponse<Object>(StatusResponse.SUCCESSFUL,"Found!!",voucherResponses);
    }

}
