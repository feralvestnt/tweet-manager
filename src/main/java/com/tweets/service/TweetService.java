package com.tweets.service;

import com.tweets.entities.Tweet;
import com.tweets.entities.User;
import com.tweets.exceptions.ValidationException;
import com.tweets.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TweetService {

    @Value("${app.minimumMumberOfFollowers}")
    private Long MINIMUM_NUMBER_OF_FOLLOWERS;

    @Value("${app.twitter.apiKey}")
    private String apiKey;

    @Value("${app.twitter.apiSecretKey}")
    private String apiSecretKey;

    private static TweetRepository tweetRepository;
    private static IdiomService idiomService;
    private static UserService userService;
    private static AccessTokenService accessTokenService;

    public TweetService(TweetRepository tweetRepository, IdiomService idiomService, UserService userService,
            AccessTokenService accessTokenService) {
        this.tweetRepository = tweetRepository;
        this.idiomService = idiomService;
        this.userService = userService;
        this.accessTokenService = accessTokenService;
    }

    public void save(Tweet tweet) {
        try {
            validateTweetBeforeCreate(tweet);

            User user = userService.save(tweet.getUser());

            tweet.setUser(user);

            tweetRepository.save(tweet);

            publishTweet(tweet.getTexto());
        } catch (TwitterException ex) {
            log.error("TwitterException: " + ex.getMessage());
        } catch (ValidationException ex) {
            log.error("Exception method save: " + ex.getMessage());
            throw ex;
        }
    }

    private void validateTweetBeforeCreate(Tweet tweet) {
        if (tweet.getUser() == null) {
            throw new ValidationException("User not informed");
        }
        if (tweet.getUser().getFollowersCount() <= MINIMUM_NUMBER_OF_FOLLOWERS) {
            throw new ValidationException("It was impossible to publish this Tweet. The number of followers must be " +
                    "bigger than " + MINIMUM_NUMBER_OF_FOLLOWERS);
        }
        if (tweet.getIdiom() == null) {
            throw new ValidationException("Idiom was not informed");
        }
        if (!idiomService.verifyIfIdiomExists(tweet.getIdiom().getId())) {
            throw new ValidationException("Idiom informed was not found");
        }
        if (tweet.getTexto().isEmpty()) {
            throw new ValidationException("Text not informed");
        }
        if (tweet.getUser().getLocation() == null || tweet.getUser().getLocation().isEmpty()) {
            throw new ValidationException("Location not informed");
        }
        if (tweet.getValid() == null) {
            throw new ValidationException("Validation not informed");
        }
    }

    private void publishTweet(String text) throws TwitterException {
        AccessToken accessToken = accessTokenService.loadAccessToken();
        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(apiKey, apiSecretKey);
        twitter.setOAuthAccessToken(accessToken);
        Status status = twitter.updateStatus(text);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }

    public Iterable<Tweet> getAll() {
        return tweetRepository.findAll();
    }

    public void validate(Tweet tweet) {
        try {
            Optional<Tweet> tweetFound = tweetRepository.findById(tweet.getId());

            verify(tweetFound);

            tweetFound.get().setValid(true);

            tweetRepository.save(tweetFound.get());
        } catch (ValidationException ex) {
            log.info("ValidationException: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception method validate: " + ex.getMessage());
            throw ex;
        }
    }

    public void verify(Optional<Tweet> tweet) throws ValidationException {
        if (!tweet.isPresent()) {
            throw new ValidationException("Tweet not found");
        }
        if (tweet.get().getValid()) {
            throw new ValidationException("This tweet was already validated");
        }
    }

    public List<Tweet> getAllValidated() {
        List<Tweet> tweets = (List) tweetRepository.findAll();
        return tweets.stream().filter(tweet -> tweet.getValid()).collect(Collectors.toList());
    }

    public Iterable<Tweet> getByMostUsedHashTag(Integer limit) {
        /*TODO finalizar
        encontrar hashtags dentro dos textos e classificar por mais usadas
        * */
        List<Tweet> tweets = (List) tweetRepository.findAll();
        if (limit == null) {

        }
        return null;
    }
}
