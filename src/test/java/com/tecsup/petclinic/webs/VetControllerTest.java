package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
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


@AutoConfigureMockMvc
@SpringBootTest
public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testFindVetOK() throws Exception {
        VetDTO vet = new VetDTO(1, "James", "Carter");
        Mockito.when(vetService.findById(1)).thenReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Jose")))
                .andExpect(jsonPath("$.lastName", is("Carter")));
    }

    @Test
    public void testFindVetKO() throws Exception {
        Mockito.when(vetService.findById(999))
                .thenThrow(new VetNotFoundException("Vet not found"));

        mockMvc.perform(get("/vets/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVet() throws Exception {
        VetDTO newVet = new VetDTO(10, "Helen", "Leary");
        Mockito.when(vetService.create(Mockito.any())).thenReturn(newVet);

        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVet))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Helen")))
                .andExpect(jsonPath("$.lastName", is("Leary")));
    }
}
