package vn.cmc.du21.orderservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.orderservice.common.JwtTokenProvider;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.presentation.external.mapper.VoucherMapper;
import vn.cmc.du21.orderservice.presentation.external.mapper.VoucherUserMapper;
import vn.cmc.du21.orderservice.presentation.external.request.VoucherRequest;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherUserResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;
import vn.cmc.du21.orderservice.service.VoucherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1.0/voucher")
public class VoucherController {
    @Autowired
    Environment env;
    @Autowired
    VoucherService voucherService;

    @GetMapping("/{voucherId}")
    ResponseEntity<Object> getDetailVoucher(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable(name = "voucherId") long voucherId) throws Throwable {

        log.info("Mapped getDetailVoucher method {{GET: /voucher/{voucherId}}}");

        VoucherResponse voucherResponse = VoucherMapper.convertVoucherToVoucherResponse(
                voucherService.getDetailVoucherById(voucherId)
        );

        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Found!!",
                        voucherResponse)
        );

    }

    @PostMapping("/save-voucher")
    ResponseEntity<Object> saveVoucher(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody VoucherRequest voucherRequest) throws Throwable {

        log.info("Mapped save voucher method {{POST: /voucher/save-voucher/{codeVoucher}");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        voucherService.saveVoucher(userId, voucherRequest.getCodeVoucher());

        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Saved")
        );
    }

    @GetMapping("/get-my-voucher")
    ResponseEntity<Object> getMyVoucher(HttpServletRequest request, HttpServletResponse response
                                        ,@RequestParam(value = "status", required = false) String status) throws Throwable {

        log.info("Mapped get my voucher method {{GET: /voucher/get-my-voucher");

      UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
      long userId = userLogin.getUserId();

        if(status.equals("") || status == null){
            status = "all";
        }else {
            status="available";
        }

        List<VoucherUserResponse> voucherUserResponse = voucherService.getMyVoucher(userId,status)
                .stream().map(VoucherUserMapper::convertVoucherUserTpVoucherResponse).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Found!!",
                        voucherUserResponse)
        );
    }

    @GetMapping("/get-list-voucher")
    ResponseEntity<Object> getListVoucher(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        log.info("Mapped get list voucher method {{GET: /voucher/get-my-voucher");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        List<VoucherResponse> voucherResponses =
                voucherService.getListVoucher(userId)
                        .stream()
                        .map(VoucherMapper::convertVoucherToVoucherResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Found!!",
                        voucherResponses)
        );
    }
}
