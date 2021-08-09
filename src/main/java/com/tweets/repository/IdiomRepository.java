package com.tweets.repository;

import com.tweets.entities.Idiom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdiomRepository extends CrudRepository<Idiom, Integer> {

}
