package com.tweets.rest;

import com.tweets.entities.Idiom;
import com.tweets.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/idiom")
public class IdiomController {

    private static IdiomService idiomService;

    @Autowired
    public IdiomController(IdiomService idiomService) {
        this.idiomService = idiomService;
    }

    @GetMapping("/get-all")
    public Iterable<Idiom> getall() {
        return idiomService.getAll();
    }
}
