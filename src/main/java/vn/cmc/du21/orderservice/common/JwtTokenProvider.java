package vn.cmc.du21.orderservice.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;

@Slf4j
public class JwtTokenProvider {
    private static Environment env;
    private JwtTokenProvider() {
        throw new IllegalStateException("Utility class");
    }

    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private static final String JWT_SECRET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //Thời gian có hiệu lực của chuỗi jwt
    private static final long JWT_AMOUNT_TO_ADD_TIME = 1;
    private static final ChronoUnit JWT_TIME_UNIT = ChronoUnit.DAYS;

    public static UserResponse getInfoUserFromToken(HttpServletRequest request)
    {
        log.info("Mapped getInfoUserFromToken method");
        String[] arr = request.getHeader("Authorization").split(" ");
        String token = arr[1];
        final String uri = env.getProperty("path.user-service") + "/api/v1.0/authentication/verify?token=" + token;

        RestTemplate restTemplate = new RestTemplate();
        UserResponse userLogin = restTemplate.getForObject(uri, UserResponse.class);
        return userLogin;
    }
}
