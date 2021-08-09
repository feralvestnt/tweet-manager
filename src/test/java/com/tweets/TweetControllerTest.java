package com.tweets;

import com.tweets.builder.IdiomBuilder;
import com.tweets.builder.TweetBuilder;
import com.tweets.builder.UserBuilder;
import com.tweets.entities.Idiom;
import com.tweets.entities.Tweet;
import com.tweets.entities.User;
import com.tweets.repository.TweetRepository;
import com.tweets.util.JacksonConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonConverter jacksonConverter;

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    public void shouldCreateTweet() throws Exception {
        Idiom idiom = new IdiomBuilder().setId(1).build();
        User user = new UserBuilder().setId(1).build();

        Tweet tweet = new TweetBuilder().setValid(false).setTexto("Novo twitter texto")
                .setHashtag(Arrays.asList("#novotweet"))
                .setUser(user).setIdiom(idiom).build();

        mockMvc.perform(post("/api/tweet/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonConverter.toJson(tweet))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAll() throws Exception {
        mockMvc.perform(get("/api/tweet/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldGetAllValidated() throws Exception {
        mockMvc.perform(get("/api/tweet/get-all-validated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldValidate() throws Exception {
        Tweet tweet = new TweetBuilder().setId(1).build();

        mockMvc.perform(put("/api/tweet/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonConverter.toJson(tweet))).andDo(print())
                .andExpect(status().isOk());

        Optional<Tweet> tweetFound = tweetRepository.findById(1);

        Assertions.assertEquals(true, tweetFound.isPresent());
        Assertions.assertEquals(true, tweetFound.get().getValid());
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        Tweet tweet = new TweetBuilder().setId(999999).build();

        mockMvc.perform(put("/api/tweet/validate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonConverter.toJson(tweet))).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Tweet not found")));
    }

    @Test
    public void shouldReturnAlreadyValidated() throws Exception {
        Tweet tweet = new TweetBuilder().setId(2).build();

        mockMvc.perform(put("/api/tweet/validate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonConverter.toJson(tweet))).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("This tweet was already validated")));
    }
}
