package panomete.project.cometopimabe.security.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import panomete.project.cometopimabe.security.entity.Users;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serial;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ID = "id";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_CREATED = "created";
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String SECRET;


    public String generateJWT(Users user, Long expiration) {
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, user.getId().toString());
        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
        claims.put(CLAIM_KEY_ROLE, user.getSimpleAuthorities());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, expiration);
    }

    public String refreshJWT(String token, Long expiration) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, expiration);
    }

    public String generateToken(Map<String, Object> claims, Long expiration){
        final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(generateExpirationDate(expiration))
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date generateExpirationDate(Long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public Date getCreatedDateFromToken(String token) {
        return new Date((Long) getClaimsFromToken(token).get(CLAIM_KEY_CREATED)) ;
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isTokenValid(String token, Users user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return created.before(lastPasswordReset);
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return (!isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token));
    }
}
