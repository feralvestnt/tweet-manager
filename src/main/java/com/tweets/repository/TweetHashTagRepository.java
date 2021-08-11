package com.tweets.repository;

import com.tweets.entities.TweetHashTag;
import org.springframework.data.repository.CrudRepository;

public interface TweetHashTagRepository extends CrudRepository<TweetHashTag, Integer> {
}
