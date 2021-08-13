package com.tweets.rest;

import com.tweets.entities.Tweet;
import com.tweets.service.HashTagService;
import com.tweets.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tweet")
public class TweetController {

    private static TweetService tweetService;
    private static HashTagService hashTagService;

    @Autowired
    public TweetController(TweetService tweetService, HashTagService hashTagService) {
        this.tweetService = tweetService;
        this.hashTagService = hashTagService;
    }

    @PostMapping("/create")
    public void create(@RequestBody Tweet tweet) {
        tweetService.create(tweet);
    }

    @GetMapping("/get-all")
    public Iterable<Tweet> getall() {
        return tweetService.getAll();
    }

    @PutMapping("/validate")
    public void validate(@RequestBody Tweet tweet) {
        tweetService.validate(tweet);
    }

    @GetMapping("/get-all-validated")
    public List<Tweet> getAllValidated() {
        return tweetService.getAllValidated();
    }

    @GetMapping("/get-most-used-hashtag")
    public List<String> getMostUsedHashTag() {
        return hashTagService.getByMostUsedHashTag();
    }
}
