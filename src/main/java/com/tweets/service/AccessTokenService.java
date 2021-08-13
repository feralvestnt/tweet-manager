package com.tweets.service;

import com.tweets.entities.AccessAuth;
import com.tweets.repository.AccessAuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class AccessTokenService {

    @Value("${app.twitter.apiKey}")
    private String API_KEY;

    @Value("${app.twitter.apiSecretKey}")
    private String API_SECRET_KEY;

    private static AccessAuthRepository accessAuthRepository;

    @Autowired
    public AccessTokenService(AccessAuthRepository accessAuthRepository) {
        this.accessAuthRepository = accessAuthRepository;
    }

    public AccessToken loadAccessToken() {
        List<AccessAuth> accessAuthList = (List) accessAuthRepository.findAll();

        if (accessAuthList.isEmpty()) {
            AccessToken accessToken = loadAccessTokenAuth();
            saveAccessToken(accessToken);
            return accessToken;
        } else {
            AccessAuth accessAuth = accessAuthList.get(0);
            return new AccessToken(accessAuth.getToken(), accessAuth.getTokenSecret());
        }
    }

    private void saveAccessToken(AccessToken accessToken) {
        AccessAuth accessAuth = new AccessAuth();
        accessAuth.setToken(accessToken.getToken());
        accessAuth.setTokenSecret(accessToken.getTokenSecret());
        accessAuthRepository.save(accessAuth);
    }

    public AccessToken loadAccessTokenAuth() {
        AccessToken accessToken = null;
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer(API_KEY, API_SECRET_KEY);
            RequestToken requestToken = twitter.getOAuthRequestToken();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            log.info("Please, Open this URL in your browser and grant access to your account:");
            log.info(requestToken.getAuthorizationURL());
            log.info("Copy and paste the PIN here:");
            String pin = br.readLine();

            accessToken = getOAuthAccessToken(pin, twitter, requestToken);
            log.info("ID " + twitter.verifyCredentials().getId() + " - AccessToken " + accessToken);
        } catch(Exception ex) {
            log.error("ERROR loadAccessToken " + ex.getMessage());
        }
        return accessToken;
    }

    public AccessToken getOAuthAccessToken(String pin, Twitter twitter, RequestToken requestToken) {
        try {
            if (pin.length() > 0) {
                return twitter.getOAuthAccessToken(requestToken, pin);
            } else {
                return twitter.getOAuthAccessToken();
            }
        } catch (TwitterException te) {
            log.info("STATUS CODE [" + te.getStatusCode() + "] MESSAGE [" + te.getMessage() + "]");
            throw new IllegalArgumentException("It was not possible to get AccessToken");
        }
    }
}
