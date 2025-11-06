package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialtyControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpecialtyService specialtyService;

    @Test
    public void testFindSpecialtyOK() throws Exception {
        SpecialtyDTO specialty = new SpecialtyDTO(1, "Radiology");
        Mockito.when(specialtyService.findById(1)).thenReturn(specialty);

        mockMvc.perform(get("/specialties/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Radiology")));
    }

    @Test
    public void testFindSpecialtyKO() throws Exception {
        Mockito.when(specialtyService.findById(999))
                .thenThrow(new SpecialtyNotFoundException("Specialty not found"));

        mockMvc.perform(get("/specialties/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = new SpecialtyDTO(10, "Surgery");
        Mockito.when(specialtyService.create(Mockito.any())).thenReturn(newSpecialty);

        mockMvc.perform(post("/specialties")
                        .content(om.writeValueAsString(newSpecialty))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Surgery")));
    }
}
