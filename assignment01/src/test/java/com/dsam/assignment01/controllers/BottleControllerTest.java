package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.models.Bottle;
import com.dsam.assignment01.services.BottleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = BottleController.class, properties = "spring.main.lazy-initialization=true")
public class BottleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BottleServiceImpl bottleService;

    @Test
    public void givenNoParameter_whenGetAddBottles_thenReturnSuccess() throws Exception {
        Mockito.when(bottleService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/bottles"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenNoParameter_whenGetAddBottleForm_thenReturnSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/bottles/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenAddBottleForm_whenPostAddBottleForm_thenReturnSuccess() throws Exception {
        String name = "name";
        MockMultipartFile image = new MockMultipartFile("New file", new byte[0]);
        String beveragePic = "https://example.com/bottle.png";
        Double beveragePrice = 5.0;
        Double alcoholPercent = 2.0;
        Double volume = 1.0;
        String supplier= "New_bottle";
        Integer bottleInStock = 10;
        AddBottleForm addBottleForm = new AddBottleForm(name, image, beveragePic, beveragePrice, alcoholPercent, volume, supplier, bottleInStock);
        String json = addBottleForm.toJson();

        Mockito.when(bottleService.createBottle(any(AddBottleForm.class))).thenReturn(new Bottle());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bottles/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
