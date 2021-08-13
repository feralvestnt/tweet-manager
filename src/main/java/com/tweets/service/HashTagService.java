package com.tweets.service;

import com.tweets.entities.HashTag;
import com.tweets.entities.Tweet;
import com.tweets.entities.TweetHashTag;
import com.tweets.repository.HashTagRepository;
import com.tweets.repository.TweetHashTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

@Service
@Slf4j
public class HashTagService {

    @Value("${app.hashTagFilter}")
    private Integer HASH_TAG_FILTER;

    private static HashTagRepository hashTagRepository;
    private static TweetHashTagRepository tweetHashTagRepository;

    @Autowired
    public HashTagService(HashTagRepository hashTagRepository, TweetHashTagRepository tweetHashTagRepository) {
        this.hashTagRepository = hashTagRepository;
        this.tweetHashTagRepository = tweetHashTagRepository;
    }

    public void saveHashTag(Tweet tweet) {
        tweet.getHashtag().forEach(hashtag -> {
            log.info("Saving Hashtag");
            HashTag hashTagSaved = hashTagRepository.save(new HashTag(null, hashtag));
            log.info("Saving TweetHashtag");
            tweetHashTagRepository.save(new TweetHashTag(null, tweet, hashTagSaved));
        });
    }

    public List<String> getByMostUsedHashTag() {
        List<HashTag> hashTagList = (List) hashTagRepository.findAll();

        Map<String, Integer> mapHashtag = new HashMap<>();

        hashTagList.forEach(h -> {
            if (mapHashtag.containsKey(h.getTexto())) {
                mapHashtag.put(h.getTexto(), 1 + mapHashtag.get(h.getTexto()));
            } else {
                mapHashtag.put(h.getTexto(), 1);
            }
        });

        List<Map.Entry<String, Integer>> list = mapHashtag.entrySet().stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());

        Integer maxFilterSize = Math.min(HASH_TAG_FILTER, list.size());

        return list.subList(0, maxFilterSize).stream().map(h -> h.getKey()).sorted().collect(Collectors.toList());
    }
}
