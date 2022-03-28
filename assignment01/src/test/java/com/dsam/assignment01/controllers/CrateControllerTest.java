package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.models.Crate;
import com.dsam.assignment01.services.BottleServiceImpl;
import com.dsam.assignment01.services.CrateServiceImpl;
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
@WebMvcTest(value = CrateController.class, properties = "spring.main.lazy-initialization=true")
public class CrateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CrateServiceImpl cratesService;

    @MockBean
    BottleServiceImpl bottleService;

    @Test
    public void givenNoParameter_whenGetCrates_thenReturnSuccess() throws Exception {
        Mockito.when(cratesService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/crates"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenNoParameter_whenGetAddCrateForm_thenReturnSuccess() throws Exception {
        Mockito.when(bottleService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/crates/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenAddCrateForm_whenPostAddCrateForm_thenReturnSuccess() throws Exception {
        long id = 1;
        String name = "name";
        MockMultipartFile image = new MockMultipartFile("New file", new byte[0]);
        String beveragePic = "https://example.com/crate.png";
        Double beveragePrice = 5.0;
        Double alcoholPercent = 2.0;
        Integer noOfBottles = 10;
        Integer bottleInStock = 10;
        AddCrateForm addCrateForm = new AddCrateForm(id, name, image, beveragePic, beveragePrice, alcoholPercent,noOfBottles, bottleInStock);
        String json = addCrateForm.toJson();

        Mockito.when(cratesService.createCrate(any(AddCrateForm.class))).thenReturn(new Crate());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/crates/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
