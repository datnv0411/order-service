package vn.cmc.du21.orderservice.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.inventoryservice.presentation.internal.response.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class JwtTokenProvider {
    private JwtTokenProvider() {
        throw new IllegalStateException("Utility class");
    }

    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private static final String JWT_SECRET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //Thời gian có hiệu lực của chuỗi jwt
    private static final long JWT_AMOUNT_TO_ADD_TIME = 1;
    private static final ChronoUnit JWT_TIME_UNIT = ChronoUnit.DAYS;

    // Tạo ra jwt từ thông tin user
    public static String generateToken(long sessionId) {
        log.info("chay den gen Token r nhé");
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(JWT_AMOUNT_TO_ADD_TIME, JWT_TIME_UNIT));
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(String.valueOf(sessionId))
                .setIssuedAt(expiryDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin session từ jwt
    public static long getSessionIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    // Lấy thông tin session từ jwt
    public static Date getExpireTimeFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public static UserResponse getInfoUserFromToken(HttpServletRequest request)
    {
        log.info("Mapped getInfoUserFromToken method");
        String[] arr = request.getHeader("Authorization").split(" ");
        String token = arr[1];
        final String uri = "http://192.168.66.125:8100/api/v1.0/authentication/verify?token=" + token;

        RestTemplate restTemplate = new RestTemplate();
        UserResponse userLogin = restTemplate.getForObject(uri, UserResponse.class);
        return userLogin;
    }
}
