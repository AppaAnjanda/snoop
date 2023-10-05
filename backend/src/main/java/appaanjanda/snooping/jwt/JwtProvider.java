package appaanjanda.snooping.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import appaanjanda.snooping.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.password}")
    private String secretKey;

    @Value("${jwt.atk}")
    private Long TOKEN_VALID_TIME;

    @Value("${jwt.rtk}")
    private Long REFRESH_TOKEN_VALID_TIME;


    public String createAccessToken(Member member) {
        Date now = new Date();
        Date expiration = expiration(now, TOKEN_VALID_TIME);
        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("username", member.getEmail());
        claims.put("time", expiration);
        return createJwt("ACCESS_TOKEN", now, expiration, claims);
    }

    private Date expiration(Date now, Long validTime){
        return new Date(now.getTime() + validTime);
    }

    public String createRefreshToken(Member users) {
        Date now = new Date();
        Date expiration = expiration(now, REFRESH_TOKEN_VALID_TIME);
        Claims claims = Jwts.claims();
        claims.put("id", users.getId());
        claims.put("username", users.getEmail());
        return createJwt("REFRESH_TOKEN", now, expiration, claims);
    }

    public String createJwt(String subject, Date now,  Date expiration, Claims claim) {

        // 만료기간 1일
        JwtBuilder jwtBuilder = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setSubject(subject)
            .setIssuedAt(now)
            .signWith(
                Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256
            );

        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }

        jwtBuilder.setExpiration(expiration);

        return jwtBuilder.compact();

    }


    //토큰검증
    public boolean validateToken(String token) {
        try {
            log.info("token={}",token);
            log.info("body={}",Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(removeBearer(token)));

            Date expiration = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(removeBearer(token)).getBody().getExpiration();
            log.info("expiration={}",expiration);

            Date now = new Date();

            return !Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(removeBearer(token))
                    .getBody()
                    .getExpiration()
                    .before(now);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("토큰 검증 실패");
        }
    }

    public MembersInfo getClaim(String token) {
        Claims claimsBody = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(removeBearer(token))
                .getBody();

        return MembersInfo.builder()
                .id(Long.valueOf((Integer) claimsBody.getOrDefault("id", 0L)))
                .username(claimsBody.getOrDefault("username", "").toString())
                .build();
    }

    //==토큰 앞 부분('Bearer') 제거 메소드==//
    private String removeBearer(String token) {
        return token.replace("Bearer", "").trim();
    }
}