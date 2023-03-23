package svl.challenge.usersapirestful.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import svl.challenge.usersapirestful.domain.model.RequestUserModel;
import svl.challenge.usersapirestful.domain.entity.User;
import svl.challenge.usersapirestful.domain.model.ResponseUserCreationModel;
import svl.challenge.usersapirestful.domain.model.ResposeUserModel;

@Service
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User userMap(RequestUserModel userModel) {
        User user = User.builder()
                .name(userModel.getName())
                .email(userModel.getEmail())
                .phones(userModel.getPhones())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .build();
        return user;
    }

    public ResponseUserCreationModel userCreationResponseMap(User user) {
        ResponseUserCreationModel userCreationResponseMap = ResponseUserCreationModel.builder()
                .id(user.getId())
                .created(user.getCreated())
                .modified(user.getModified())
                .last_login(user.getLast_login())
                .token(user.getToken())
                .isactive(user.isIsactive())
                .build();
        return userCreationResponseMap;
    }

    public ResposeUserModel userResponseMap(User user) {
        ResposeUserModel resposeUserModel = ResposeUserModel.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phones(user.getPhones())
                .created(user.getCreated())
                .modified(user.getModified())
                .last_login(user.getLast_login())
                .token(user.getToken())
                .isactive(user.isIsactive())
                .build();
        return resposeUserModel;
    }

    public User updateUser(RequestUserModel userModel, User user) {
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setPhones(userModel.getPhones());
        user.setPassword(userModel.getPassword());
        return user;
    }

}
