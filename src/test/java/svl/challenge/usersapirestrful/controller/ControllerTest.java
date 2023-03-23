package svl.challenge.usersapirestrful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import svl.challenge.usersapirestrful.domain.model.RequestLoginModel;
import svl.challenge.usersapirestrful.domain.model.RequestUserModel;
import svl.challenge.usersapirestrful.domain.model.RequestValidateModel;
import svl.challenge.usersapirestrful.domain.model.ResponseModel;
import svl.challenge.usersapirestrful.service.UserService;

import java.util.ArrayList;


@WebMvcTest(Controller.class)
@AutoConfigureMockMvc(addFilters = false)
public class ControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        RequestUserModel requestUserModel = RequestUserModel.builder().email("test@test.com").name("test").password("testTest1234").phones(new ArrayList<>()).build();
        RequestBuilder request = MockMvcRequestBuilders.post("/users/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestUserModel));
        Mockito.when(userService.createUser(Mockito.any(RequestUserModel.class))).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getByMail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/users/get/email").param("email", "test@test.com");
        Mockito.when(userService.getUserByMail(Mockito.anyString())).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/users/get/id").param("id", "test");
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/users/get/all");
        Mockito.when(userService.getAll()).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void edit() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.put("/users/edit").contentType(MediaType.APPLICATION_JSON).param("currentEmail", "test").content(asJsonString(new RequestUserModel()));
        Mockito.when(userService.edit(Mockito.anyString(), Mockito.any(RequestUserModel.class))).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteByMail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/users/delete/email").param("email", "test@test.com");
        Mockito.when(userService.deleteUserByMail(Mockito.anyString())).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/users/delete/id").param("id", "test");
        Mockito.when(userService.deleteUserById(Mockito.anyString())).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void login() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(RequestLoginModel.builder().email("test").password("test").build()));
        Mockito.when(userService.login(Mockito.any(RequestLoginModel.class))).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void logout() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users/logout").contentType(MediaType.APPLICATION_JSON).content(asJsonString(RequestValidateModel.builder().email("test").token("test").build()));
        Mockito.when(userService.logout(Mockito.any(RequestValidateModel.class))).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void validateToken() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/users/validate/token").contentType(MediaType.APPLICATION_JSON).content(asJsonString(RequestValidateModel.builder().email("test").token("test").build()));
        Mockito.when(userService.validateToken(Mockito.any(RequestValidateModel.class))).thenReturn(new ResponseModel());
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
