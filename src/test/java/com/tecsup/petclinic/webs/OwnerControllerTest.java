package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.*;
import com.tecsup.petclinic.exceptions.*;
import com.tecsup.petclinic.services.*;
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
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testFindOwnerOK() throws Exception {
        OwnerDTO owner = new OwnerDTO(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
        Mockito.when(ownerService.findById(1)).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("George")))
                .andExpect(jsonPath("$.lastName", is("Franklin")));
    }

    @Test
    public void testFindOwnerKO() throws Exception {
        Mockito.when(ownerService.findById(999))
                .thenThrow(new OwnerNotFoundException("Owner not found"));

        mockMvc.perform(get("/owners/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOwner() throws Exception {
        OwnerDTO newOwner = new OwnerDTO(10, "Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749");
        Mockito.when(ownerService.create(Mockito.any())).thenReturn(newOwner);

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Betty")))
                .andExpect(jsonPath("$.lastName", is("Davis")));
    }
}
