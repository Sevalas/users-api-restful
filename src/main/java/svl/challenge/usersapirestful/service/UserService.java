package svl.challenge.usersapirestful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import svl.challenge.usersapirestful.domain.model.RequestLoginModel;
import svl.challenge.usersapirestful.domain.model.RequestValidateModel;
import svl.challenge.usersapirestful.domain.model.RequestUserModel;
import svl.challenge.usersapirestful.domain.model.ResponseModel;
import svl.challenge.usersapirestful.domain.dto.TokenDto;
import svl.challenge.usersapirestful.domain.entity.User;
import svl.challenge.usersapirestful.domain.model.ResponseUserCreationModel;
import svl.challenge.usersapirestful.domain.model.ResposeUserModel;
import svl.challenge.usersapirestful.repository.UserRepository;
import svl.challenge.usersapirestful.mapper.UserMapper;
import svl.challenge.usersapirestful.security.JWTProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;


    public ResponseModel createUser(RequestUserModel userModel) {
        try {
            if (userExist(userModel.getEmail())) {
                return new ResponseModel("El correo ya registrado", false);
            }
            if (!checkRegexMail(userModel.getEmail())) {
                return new ResponseModel("Formato Invalido, El correo electrónico debe seguir el formato (email@dominio.cl)", false);
            }
            if (!checkRegexPassword(userModel.getPassword())) {
                return new ResponseModel("Formato Invalido, La contraseña debe tener minimo 12 caracteres e incluir (una letra mayúscula, una letras minúsculas y dos numeros)", false);
            }
            User newUser = userMapper.userMap(userModel);
            newUser.setToken(getToken(newUser));
            newUser = userRepository.save(newUser);
            ResponseUserCreationModel userCreationResponse = userMapper.userCreationResponseMap(newUser);
            return new ResponseModel("Usuario Creado exitosamente", userCreationResponse);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel getUserByMail(String Email) {
        try {
            Optional<User> user = userRepository.findByEmail(Email);
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            ResposeUserModel resposeUserModel = userMapper.userResponseMap(user.get());
            return new ResponseModel("Usuario encontrado", resposeUserModel);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel getUserById(String Id) {
        try {
            Optional<User> user = userRepository.findById(Id);
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            ResposeUserModel resposeUserModel = userMapper.userResponseMap(user.get());
            return new ResponseModel("Usuario encontrado", resposeUserModel);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel deleteUserByMail(String Email) {
        try {
            Optional<User> user = userRepository.findByEmail(Email);
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            userRepository.deleteById(user.get().getId());
            return new ResponseModel("Usuario eliminado", true);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel deleteUserById(String Id) {
        try {
            Optional<User> user = userRepository.findById(Id);
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            userRepository.deleteById(user.get().getId());
            return new ResponseModel("Usuario eliminado", true);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }


    public ResponseModel edit(String currentEmail, RequestUserModel userModel) {
        try {
            Optional<User> user = userRepository.findByEmail(currentEmail);
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            User updatedUser = userMapper.updateUser(userModel, user.get());
            updatedUser.setModified(new Date());
            updatedUser.setToken(getToken(updatedUser));
            updatedUser = userRepository.save(updatedUser);
            ResposeUserModel resposeUserModel = userMapper.userResponseMap(updatedUser);
            return new ResponseModel("Usuario actualizado exitosamente", resposeUserModel);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel getAll() {
        try {
            List<User> users = userRepository.findAll();
            List<ResposeUserModel> listOfUsers = new ArrayList<>();
            for(User user:users) {
                listOfUsers.add(userMapper.userResponseMap(user));
            }
            return new ResponseModel("Lista de usuarios", listOfUsers);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel login(RequestLoginModel loginModel) {
        try {
            Optional<User> user = userRepository.findByEmail(loginModel.getEmail());
            if (user.isPresent() && passwordEncoder.matches(loginModel.getPassword(), user.get().getPassword())) {
                updateLastLogin(user.get());
                updateToken(user.get());
                return new ResponseModel("Sesión iniciada exitosamente", new TokenDto(jwtProvider.createToken(user.get())));

            }
            return new ResponseModel("No puede iniciar sesión. Por favor, verifique su Email / Contraseña", false);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel logout(RequestValidateModel logoutModel) {
        try {
            Optional<User> user = userRepository.findByEmail(logoutModel.getEmail());
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            if (!tokenValidator(user.get())) {
                return new ResponseModel("Token invalido", false);
            }
            removeToken(user.get());
            return new ResponseModel("Sesión finalizada exitosamente", true);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    public ResponseModel validateToken(RequestValidateModel validateModel) {
        try {
            Optional<User> user = userRepository.findByEmail(validateModel.getEmail());
            if (!user.isPresent()) {
                return new ResponseModel("Usuario no encontrado", false);
            }
            if (tokenValidator(user.get())) {
                return new ResponseModel("Token valido", true);
            }
            return new ResponseModel("Token invalido", false);
        } catch (Exception error) {
            return new ResponseModel("Error interno, favor intentar nuevamente o contactar con soporte", false);
        }
    }

    private Boolean userExist(String email) {
        return true ? userRepository.findByEmail(email).isPresent() : false;
    }

    private boolean checkRegexMail(String mail) {
        return mail.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");
    }

    private boolean checkRegexPassword(String password) {
        return password.matches("^(?=.*\\d{2,})(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[a-zA-Z]).{12,}$");
    }

    private String getToken(User user) {
        return new TokenDto(jwtProvider.createToken(user)).getToken();
    }

    private void updateToken(User user) {
        String newToken = jwtProvider.createToken(user);
        userRepository.updateTokenById(newToken, user.getId());
    }

    private void removeToken(User user) {
        userRepository.deleteTokenById(user.getId());
    }

    private boolean tokenValidator(User user) {
        if (!jwtProvider.validate(user.getToken()) || !jwtProvider.validateByEmail(user.getToken(), user.getEmail())) {
            return false;
        }
        return true;
    }

    private void updateLastLogin(User user) {
        userRepository.updateLastLoginById(new Date(), user.getId());
    }

}
