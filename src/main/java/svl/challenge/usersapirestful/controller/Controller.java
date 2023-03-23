package svl.challenge.usersapirestful.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import svl.challenge.usersapirestful.domain.model.RequestLoginModel;
import svl.challenge.usersapirestful.domain.model.RequestValidateModel;
import svl.challenge.usersapirestful.domain.model.RequestUserModel;
import svl.challenge.usersapirestful.domain.model.ResponseModel;
import svl.challenge.usersapirestful.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseModel create(@Valid @RequestBody RequestUserModel userModel) {
        LOGGER.info("Executing Controller.create");
        return userService.createUser(userModel);
    }

    @GetMapping(value = "/get/email")
    public ResponseModel getByMail(@RequestParam String email) {
        LOGGER.info("Executing Controller.getByMail");
        return userService.getUserByMail(email);
    }

    @GetMapping(value = "/get/id")
    public ResponseModel getById(@RequestParam String id) {
        LOGGER.info("Executing Controller.getById");
        return userService.getUserById(id);
    }

    @GetMapping(value = "/get/all")
    public ResponseModel getAll() {
        LOGGER.info("Executing Controller.getAll");
        return userService.getAll();
    }

    @PutMapping(value = "/edit")
    public ResponseModel edit(@RequestParam String currentEmail, @RequestBody RequestUserModel userModel) {
        LOGGER.info("Executing Controller.edit");
        return userService.edit(currentEmail, userModel);
    }

    @DeleteMapping(value = "/delete/email")
    public ResponseModel deleteByMail(@RequestParam String email) {
        LOGGER.info("Executing Controller.deleteByMail");
        return userService.deleteUserByMail(email);
    }

    @DeleteMapping(value = "/delete/id")
    public ResponseModel deleteById(@RequestParam String id) {
        LOGGER.info("Executing Controller.deleteById");
        return userService.deleteUserById(id);
    }

    @PostMapping(value = "/login")
    public ResponseModel login(@Valid @RequestBody RequestLoginModel requestLoginModel) {
        LOGGER.info("Executing Controller.login");
        return userService.login(requestLoginModel);
    }

    @PostMapping(value = "/logout")
    public ResponseModel logout(@Valid @RequestBody RequestValidateModel requestValidateModel) {
        LOGGER.info("Executing Controller.logout");
        return userService.logout(requestValidateModel);
    }

    @GetMapping(value = "/validate/token")
    public ResponseModel validateToken(@Valid @RequestBody RequestValidateModel validateModel) {
        LOGGER.info("Executing Controller.validate");
        return userService.validateToken(validateModel);
    }

}
