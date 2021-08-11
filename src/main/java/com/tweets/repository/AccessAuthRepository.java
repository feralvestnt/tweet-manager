package com.tweets.repository;

import com.tweets.entities.AccessAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessAuthRepository extends CrudRepository<AccessAuth, Long> {
}
