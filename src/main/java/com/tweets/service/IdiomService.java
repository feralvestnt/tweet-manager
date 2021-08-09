package com.tweets.service;

import com.tweets.entities.Idiom;
import com.tweets.repository.IdiomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomService {

    private static IdiomRepository idiomRepository;

    @Autowired
    public IdiomService(IdiomRepository idiomRepository) {
        this.idiomRepository = idiomRepository;
    }

    public Iterable<Idiom> getAll() {
        return idiomRepository.findAll();
    }

    public boolean verifyIfIdiomExists(Integer idiomId) {
        Optional<Idiom> idiomOptional = idiomRepository.findById(idiomId);

        return idiomOptional.isPresent();
    }
}
