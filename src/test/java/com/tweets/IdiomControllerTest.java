package com.tweets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class IdiomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetAll() throws Exception {
        mockMvc.perform(get("/api/idiom/get-all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }
}
