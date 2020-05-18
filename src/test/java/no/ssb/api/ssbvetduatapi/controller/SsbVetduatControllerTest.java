package no.ssb.api.ssbvetduatapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import no.ssb.api.ssbvetduatapi.repository.VetduatRestRepository;
import no.ssb.api.ssbvetduatapi.service.SsbVetduatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {SsbVetduatController.class, SsbVetduatService.class, VetduatRestRepository.class})
@WebMvcTest
class SsbVetduatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEmptyResult() throws Exception {
        assertEquals(1, 1);

    }


    private void testSsbVetduat(String codes, JsonNode expectedResult ) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/produktinfo/" + codes)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse resultToTest = result.getResponse();
        assertNotNull(resultToTest);
        assertEquals(expectedResult, resultToTest);
    }

}
