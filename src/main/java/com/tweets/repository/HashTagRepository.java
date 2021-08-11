package com.tweets.repository;

import com.tweets.entities.HashTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends CrudRepository<HashTag, Integer> {
}
