package com.tweets.service;

import com.tweets.entities.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Slf4j
@Service
public class TwitterPublisherService {

    @Value("${app.twitter.apiKey}")
    private String API_KEY;

    @Value("${app.twitter.apiSecretKey}")
    private String API_SECRET_KEY;

    private static AccessTokenService accessTokenService;

    @Autowired
    public TwitterPublisherService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    public void publishTweet(Tweet tweet) {
        try {
            AccessToken accessToken = accessTokenService.loadAccessToken();
            TwitterFactory factory = new TwitterFactory();
            Twitter twitter = factory.getInstance();
            twitter.setOAuthConsumer(API_KEY, API_SECRET_KEY);
            twitter.setOAuthAccessToken(accessToken);

            String hashTags = String.join(" ", tweet.getHashtag());
            String content = tweet.getTexto() + hashTags;
            Status status = twitter.updateStatus(content);
            log.info("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException ex) {
            log.error("TwitterException: " + ex.getMessage());
        } catch (Exception ex) {
            log.info("Exception method publishTweet " + ex.getMessage());
        }
    }
}
