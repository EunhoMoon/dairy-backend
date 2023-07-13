package me.janek.dairy.common.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.janek.dairy.domain.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static me.janek.dairy.common.jwt.JwtTokenProvider.TokenStatus.*;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key SECRET_KEY;

    private final long EXPIRATION_DIFF;

    private final UserService userService;

    public JwtTokenProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration-time}") long expirationTime,
        UserService userService
    ) {
        this.EXPIRATION_DIFF = expirationTime * 1000;
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
        this.userService = userService;
    }

    public String createToken(String userEmail) {
//        var authorities = authentication.getAuthorities().stream()
//            .map(GrantedAuthority::getAuthority)
//            .collect(Collectors.joining(","));
        var claims = Jwts.claims();
        claims.put("userEmail", userEmail);

        var now = new Date().getTime();
        var expirationTime = new Date(now + EXPIRATION_DIFF);

        return Jwts.builder()
            .setSubject(userEmail)
            .claim("userEmail", userEmail)
            .signWith(SECRET_KEY, HS512)
            .setExpiration(expirationTime)
            .compact();
    }

    public Authentication getAuthentication(String jwtToken) {
        var claims = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(jwtToken)
            .getBody();

        String userEmail = claims.getSubject();
        UserDetails userDetails = userService.loadUserByUsername(userEmail);

        return new UsernamePasswordAuthenticationToken(userDetails, jwtToken, new ArrayList<>());
    }

    public TokenStatus isValid(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken);
        } catch (SecurityException | MalformedJwtException e) {
            return INVALID_SIGNATURE;
        } catch (ExpiredJwtException e) {
            return EXPIRED;
        } catch (UnsupportedJwtException e) {
            return NOT_SUPPORTED;
        } catch (IllegalArgumentException e) {
            return INVALID_TOKEN;
        }
        return VALID;
    }

    @Getter
    @RequiredArgsConstructor
    public enum TokenStatus {
        VALID("검증된 JWT 토큰입니다."),
        EXPIRED("만료된 JWT 토큰입니다."),
        INVALID_SIGNATURE("잘못된 JWT 서명입니다."),
        NOT_SUPPORTED("지원되지 않는 JWT 토큰입니다."),
        INVALID_TOKEN("JWT 토큰이 잘못되었습니다.");

        private final String describe;
    }

}
