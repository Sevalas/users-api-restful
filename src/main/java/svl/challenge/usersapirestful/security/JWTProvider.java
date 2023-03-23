package svl.challenge.usersapirestful.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import svl.challenge.usersapirestful.domain.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JWTProvider {

    @Value("${jwt.secret}")
    private String secret;

    private static Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(User user) {
        Map<String, Object> claims;
        claims = Jwts.claims().setSubject(String.valueOf(user.getEmail()));
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(exp).signWith(key).compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception error) {
            return false;
        }
    }

    public boolean validateByEmail(String token, String email){
        try {
            return email.equals(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
        }catch (Exception e) {
            return false;
        }
    }
}
