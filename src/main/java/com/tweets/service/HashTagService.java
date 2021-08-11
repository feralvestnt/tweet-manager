package com.tweets.service;

import com.tweets.entities.HashTag;
import com.tweets.entities.Tweet;
import com.tweets.entities.TweetHashTag;
import com.tweets.repository.HashTagRepository;
import com.tweets.repository.TweetHashTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HashTagService {

    private static HashTagRepository hashTagRepository;
    private static TweetHashTagRepository tweetHashTagRepository;

    @Autowired
    public HashTagService(HashTagRepository hashTagRepository, TweetHashTagRepository tweetHashTagRepository) {
        this.hashTagRepository = hashTagRepository;
        this.tweetHashTagRepository = tweetHashTagRepository;
    }

    public void saveHashTag(Tweet tweet) {
        System.out.println(tweet);
        tweet.getHashtag().forEach(hashtag -> {
            log.info("Saving Hashtag");
            HashTag hashTagSaved = hashTagRepository.save(new HashTag(null, hashtag));
            log.info("Saving TweetHashtag");
            tweetHashTagRepository.save(new TweetHashTag(null, tweet, hashTagSaved));
        });
    }

    public Iterable<Tweet> getByMostUsedHashTag(Integer limit) {
        /*TODO finalizar
        encontrar hashtags dentro dos textos e classificar por mais usadas
        * */
        /*List<Tweet> tweets = (List) tweetRepository.findAll();
        if (limit == null) {

        }*/
        return null;
    }
}
