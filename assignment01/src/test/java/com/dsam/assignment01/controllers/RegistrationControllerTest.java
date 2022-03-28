package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.models.User;
import com.dsam.assignment01.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    public void givenNoParameter_whenGetRegistrationForm_thenReturnSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenRegistrationForm_whenPostRegistrationForm_thenReturnSuccess() throws Exception {
        String password = "password";
        String name = "name";
        String email = "tas@g.co";
        String street = "street";
        String house = "60";
        String postal = "12345";
        String cityState = "berlin";
        RegistrationForm registrationForm = new RegistrationForm(password, password, name, email, street, house, postal, cityState, cityState);
        String json = registrationForm.toJson();

        Mockito.when(userService.findByUsername(any(String.class))).thenReturn(null);
        Mockito.when(userService.createUser(any(RegistrationForm.class))).thenReturn(new User());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
