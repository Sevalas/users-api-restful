package svl.challenge.usersapirestrful.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import svl.challenge.usersapirestrful.domain.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTProviderTest {

    @Autowired
    private JWTProvider jWTProvider;

    @Test
    public void createToken() {
        Assert.notNull(jWTProvider.createToken(new User()), "Invalid assert");
    }

    @Test
    public void validate() {
        String token = jWTProvider.createToken(new User());
        Assert.isTrue(jWTProvider.validate(token), "Invalid assert");
    }

    @Test
    public void validateInvalid() {
        Assert.isTrue(!jWTProvider.validate("test"), "Invalid assert");
    }

    @Test
    public void validateByEmail() {
        String token = jWTProvider.createToken(User.builder().email("testMail").build());
        Assert.isTrue(jWTProvider.validateByEmail(token, "testMail"), "Invalid assert");
    }

    @Test
    public void validateByEmailInvalid() {
        String token = jWTProvider.createToken(User.builder().email("testMail").build());
        Assert.isTrue(!jWTProvider.validateByEmail(token, "invalidMail"), "Invalid assert");
    }

    @Test
    public void validateByEmailError() {
        Assert.isTrue(!jWTProvider.validateByEmail("test", "invalidMail"), "Invalid assert");
    }
}
