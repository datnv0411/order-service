package vn.cmc.du21.orderservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        VoucherResponse voucherResponse = VoucherMapper.convertVouchertoVoucherResponse(
                voucherService.getDetailVoucherById(voucherId)
        );

        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Found!!",
                        voucherResponse)
        );

    }

    @PostMapping("/save-voucher/{codeVoucher}")
    ResponseEntity<Object> SaveVocher(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable(name = "codeVoucher") String codeVoucher) throws Throwable {

        log.info("Mapped save voucher method {{POST: /voucher/save-voucher/{codeVoucher}");

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        long userId = userLogin.getUserId();

        voucherService.SaveVoucher(userId, codeVoucher);

        return ResponseEntity.ok().body(
                new StandardResponse<Object>(StatusResponse.SUCCESSFUL,
                        "Saved")
        );
    }

    @GetMapping("/get-my-voucher")
    ResponseEntity<Object> getMyVoucher(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        log.info("Mapped get my voucher method {{GET: /voucher/get-my-voucher");

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        long userId = userLogin.getUserId();

        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Found!!",
                        voucherService.getMyVoucher(userId))
        );
    }

    @GetMapping("/get-list-voucher")
    ResponseEntity<Object> getListVoucher(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        log.info("Mapped get list voucher method {{GET: /voucher/get-my-voucher");

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        long userId = userLogin.getUserId();

        List<VoucherResponse> voucherResponses =voucherService.getListVoucher(userId).stream().map(VoucherMapper::convertVouchertoVoucherResponse).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new StandardResponse<Object>(
                        StatusResponse.SUCCESSFUL,
                        "Found!!",
                        voucherResponses)
        );
    }
}
