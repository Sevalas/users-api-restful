package svl.challenge.usersapirestful.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import svl.challenge.usersapirestful.domain.dto.TokenDto;
import svl.challenge.usersapirestful.domain.entity.Phones;
import svl.challenge.usersapirestful.domain.entity.User;
import svl.challenge.usersapirestful.domain.model.RequestLoginModel;
import svl.challenge.usersapirestful.domain.model.RequestUserModel;
import svl.challenge.usersapirestful.domain.model.RequestUserUpdateModel;
import svl.challenge.usersapirestful.domain.model.RequestValidateModel;
import svl.challenge.usersapirestful.domain.model.ResponseModel;
import svl.challenge.usersapirestful.domain.model.ResponseUserCreationModel;
import svl.challenge.usersapirestful.domain.model.ResposeUserModel;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class domainTest {

    @Test
    public void tokenDto() {
        TokenDto tokenDto = new TokenDto();
        tokenDto = TokenDto.builder().build();
        tokenDto.setToken("test");
        Assert.notNull(tokenDto, "Invalid assert");
    }

    @Test
    public void phones() {
        Phones phones = new Phones();

        phones.setId("test");
        phones.setNumber("test");
        phones.setCitycode("test");
        phones.setContrycode("test");
        phones.setUsers(null);

        Phones phonesBuilder = Phones.builder()
                .id(phones.getId())
                .number(phones.getNumber())
                .citycode(phones.getCitycode())
                .contrycode(phones.getContrycode())
                .users(phones.getUsers())
                .build();

        Assert.notNull(phonesBuilder, "Invalid assert");
    }

    @Test
    public void user() {
        User user = new User();

        user.setId("test");
        user.setName("test");
        user.setEmail("test");
        user.setPassword("test");
        user.setPhones(new ArrayList<>());
        user.setCreated(null);
        user.setModified(null);
        user.setLast_login(null);
        user.setToken("test");
        user.setIsactive(true);

        User userBuilder = User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(user.getPhones())
                .created(user.getCreated())
                .modified(user.getModified())
                .last_login(user.getLast_login())
                .token(user.getToken())
                .isactive(user.isIsactive())
                .build();

        Assert.notNull(userBuilder, "Invalid assert");
    }

    @Test
    void requestLoginModel() {
        RequestLoginModel requestLoginModel = new RequestLoginModel();

        requestLoginModel.setEmail("test");
        requestLoginModel.setPassword("test");

        RequestLoginModel requestLoginModelBuilder = RequestLoginModel.builder()
                .email(requestLoginModel.getEmail())
                .password(requestLoginModel.getPassword())
                .build();

        Assert.notNull(requestLoginModelBuilder, "Invalid assert");
    }

    @Test
    void requestUserModel() {
        RequestUserModel requestUserModel = new RequestUserModel();

        requestUserModel.setName("test");
        requestUserModel.setEmail("test");
        requestUserModel.setPassword("test");
        requestUserModel.setPhones(new ArrayList<>());

        RequestUserModel requestUserModelBuilder = RequestUserModel.builder()
                .name(requestUserModel.getName())
                .email(requestUserModel.getEmail())
                .password(requestUserModel.getPassword())
                .phones(requestUserModel.getPhones())
                .build();

        Assert.notNull(requestUserModelBuilder, "Invalid assert");
    }

    @Test
    void requestUserUpdateModel() {
        RequestUserUpdateModel requestUserUpdateModel = new RequestUserUpdateModel();

        requestUserUpdateModel.setName("test");
        requestUserUpdateModel.setEmail("test");
        requestUserUpdateModel.setPassword("test");
        requestUserUpdateModel.setPhones(new ArrayList<>());

        RequestUserUpdateModel requestUserUpdateModelBuilder = RequestUserUpdateModel.builder()
                .name(requestUserUpdateModel.getName())
                .email(requestUserUpdateModel.getEmail())
                .password(requestUserUpdateModel.getPassword())
                .phones(requestUserUpdateModel.getPhones())
                .build();

        Assert.notNull(requestUserUpdateModelBuilder, "Invalid assert");
    }

    @Test
    void requestValidateModel() {
        RequestValidateModel requestValidateModel = new RequestValidateModel();

        requestValidateModel.setEmail("test");
        requestValidateModel.setToken("test");

        RequestValidateModel requestValidateModelBuilder = RequestValidateModel.builder()
                .email(requestValidateModel.getEmail())
                .token(requestValidateModel.getToken())
                .build();

        Assert.notNull(requestValidateModelBuilder, "Invalid assert");
    }

    @Test
    void responseModel() {
        ResponseModel responseModel = new ResponseModel();

        responseModel.setMensaje("test");
        responseModel.setData("test");

        ResponseModel responseModelBuilder = ResponseModel.builder()
                .mensaje(responseModel.getMensaje())
                .data(responseModel.getData())
                .build();

        Assert.notNull(responseModelBuilder, "Invalid assert");
    }

    @Test
    void responseUserCreationModel() {
        ResponseUserCreationModel responseUserCreationModel = new ResponseUserCreationModel();

        responseUserCreationModel.setId("test");
        responseUserCreationModel.setCreated(null);
        responseUserCreationModel.setModified(null);
        responseUserCreationModel.setLast_login(null);
        responseUserCreationModel.setToken("test");
        responseUserCreationModel.setIsactive(true);

        ResponseUserCreationModel responseUserCreationModelBuilder = ResponseUserCreationModel.builder()
                .id(responseUserCreationModel.getId())
                .created(responseUserCreationModel.getCreated())
                .modified(responseUserCreationModel.getModified())
                .last_login(responseUserCreationModel.getLast_login())
                .token(responseUserCreationModel.getToken())
                .isactive(responseUserCreationModel.isIsactive())
                .build();

        Assert.notNull(responseUserCreationModelBuilder, "Invalid assert");
    }

    @Test
    void resposeUserModel() {
        ResposeUserModel resposeUserModel = new ResposeUserModel();

        resposeUserModel.setId("test");
        resposeUserModel.setName("test");
        resposeUserModel.setEmail("test");
        resposeUserModel.setPhones(new ArrayList<>());
        resposeUserModel.setCreated(null);
        resposeUserModel.setModified(null);
        resposeUserModel.setLast_login(null);
        resposeUserModel.setToken("test");
        resposeUserModel.setIsactive(true);

        ResposeUserModel resposeUserModelBuilder = ResposeUserModel.builder()
                .id(resposeUserModel.getId())
                .name(resposeUserModel.getName())
                .email(resposeUserModel.getEmail())
                .phones(resposeUserModel.getPhones())
                .created(resposeUserModel.getCreated())
                .modified(resposeUserModel.getModified())
                .last_login(resposeUserModel.getLast_login())
                .token(resposeUserModel.getToken())
                .isactive(resposeUserModel.isIsactive())
                .build();

        Assert.notNull(resposeUserModelBuilder, "Invalid assert");
    }

}
