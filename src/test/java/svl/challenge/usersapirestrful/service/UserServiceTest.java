package svl.challenge.usersapirestful.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import svl.challenge.usersapirestful.domain.entity.User;
import svl.challenge.usersapirestful.domain.model.RequestLoginModel;
import svl.challenge.usersapirestful.domain.model.RequestUserModel;
import svl.challenge.usersapirestful.domain.model.RequestValidateModel;
import svl.challenge.usersapirestful.domain.model.ResponseModel;
import svl.challenge.usersapirestful.domain.model.ResponseUserCreationModel;
import svl.challenge.usersapirestful.domain.model.ResposeUserModel;
import svl.challenge.usersapirestful.repository.UserRepository;
import svl.challenge.usersapirestful.security.JWTProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JWTProvider jwtProvider;

    @Test
    public void createUserTestOk() {
        RequestUserModel requestUserModel = RequestUserModel.builder().email("test@test.com").password("testTest1234").build();
        setUserExist(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        ResponseModel result = userService.createUser(requestUserModel);
        Assert.isInstanceOf(ResponseUserCreationModel.class, result.getData());
    }

    @Test
    public void createUserExistent() {
        setUserExist(true);
        ResponseModel result = userService.createUser(RequestUserModel.builder().email("test@test.com").password("testTest1234").build());
        Assert.isTrue("El correo ya registrado".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void createUserInvalidEmail() {
        RequestUserModel requestUserModel = RequestUserModel.builder().email("test").build();
        setUserExist(false);
        ResponseModel result = userService.createUser(requestUserModel);
        Assert.isTrue("Formato Invalido, El correo electrónico debe seguir el formato (email@dominio.cl)".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void createUserInvalidPassword() {
        RequestUserModel requestUserModel = RequestUserModel.builder().email("test@test.com").password("test").build();
        setUserExist(false);
        ResponseModel result = userService.createUser(requestUserModel);
        Assert.isTrue("Formato Invalido, La contraseña debe tener minimo 12 caracteres e incluir (una letra mayúscula, una letras minúsculas y dos numeros)".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void getUserByMail() {
        setUserExist(true);
        ResponseModel result = userService.getUserByMail("test");
        Assert.isTrue("Usuario encontrado".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void getUserById() {
        setUserExist(true);
        ResponseModel result = userService.getUserById("test");
        Assert.isTrue("Usuario encontrado".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void deleteUserByMail() {
        setUserExist(true);
        ResponseModel result = userService.deleteUserByMail("test");
        Assert.isTrue("Usuario eliminado".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void deleteUserById() {
        setUserExist(true);
        ResponseModel result = userService.deleteUserById("test");
        Assert.isTrue("Usuario eliminado".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void edit() {
        RequestUserModel requestUserModel = RequestUserModel.builder().email("test@test.com").password("testTest1234").build();
        setUserExist(true);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        ResponseModel result = userService.edit("test", requestUserModel);
        Assert.isInstanceOf(ResposeUserModel.class, result.getData());
    }

    @Test
    public void getAll() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(new User()));
        ResponseModel result = userService.getAll();
        Assert.isTrue("Lista de usuarios".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void login() {
        setUserExist(true);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(new User()));
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        ResponseModel result = userService.login(RequestLoginModel.builder().email("test").password("test").build());
        Assert.isTrue("Sesión iniciada exitosamente".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void logout() {
        setUserExist(true);
        Mockito.when(jwtProvider.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(jwtProvider.validateByEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        ResponseModel result = userService.logout(RequestValidateModel.builder().email("test").token("test").build());
        Assert.isTrue("Sesión finalizada exitosamente".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void logoutInvalidToken() {
        setUserExist(true);
        Mockito.when(jwtProvider.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(jwtProvider.validateByEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        ResponseModel result = userService.logout(RequestValidateModel.builder().email("test").token("test").build());
        Assert.isTrue("Token invalido".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void validateToken() {
        setUserExist(true);
        Mockito.when(jwtProvider.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(jwtProvider.validateByEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        ResponseModel result = userService.validateToken(RequestValidateModel.builder().email("test").token("test").build());
        Assert.isTrue("Token valido".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void validateTokenInvalid() {
        setUserExist(true);
        Mockito.when(jwtProvider.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(jwtProvider.validateByEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        ResponseModel result = userService.validateToken(RequestValidateModel.builder().email("test").token("test").build());
        Assert.isTrue("Token invalido".equals(result.getMensaje()), "Not Match");
    }

    @Test
    public void unknownUsers() {
        setUserExist(false);
        ArrayList<ResponseModel> listOfResponses = new ArrayList<>(Arrays.asList(
                userService.getUserByMail("test"),
                userService.getUserById("test"),
                userService.deleteUserByMail("test"),
                userService.deleteUserById("test"),
                userService.edit("test", new RequestUserModel()),
                userService.login(RequestLoginModel.builder().email("test").build()),
                userService.logout(RequestValidateModel.builder().email("test").build()),
                userService.validateToken(RequestValidateModel.builder().email("test").build())
        ));

        for (ResponseModel result : listOfResponses) {
            Assert.isTrue(
                    Arrays.asList("Usuario no encontrado", "No puede iniciar sesión. Por favor, verifique su Email / Contraseña")
                            .contains(result.getMensaje()), "Not Match");
        }
    }

    @Test
    public void catchErrors() {
        Mockito.when(userRepository.findAll()).thenReturn(null);
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(null);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        ArrayList<ResponseModel> listOfResponses = new ArrayList<>(Arrays.asList(
                userService.createUser(null),
                userService.getUserByMail("test"),
                userService.getUserById("test"),
                userService.deleteUserByMail("test"),
                userService.deleteUserById("test"),
                userService.edit("test", null),
                userService.login(null),
                userService.logout(null),
                userService.getAll(),
                userService.validateToken(null)
        ));

        for (ResponseModel result : listOfResponses) {
            Assert.isTrue("Error interno, favor intentar nuevamente o contactar con soporte".equals(result.getMensaje()), "Not Match");
        }
    }

    private void setUserExist(boolean selector) {
        Optional<User> user = selector ? Optional.of(User.builder().email("test").password("test").token("test").build()) : Optional.empty();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(user);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);
    }

}
