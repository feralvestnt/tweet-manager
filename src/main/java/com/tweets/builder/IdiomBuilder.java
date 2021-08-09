package com.tweets.builder;

import com.tweets.entities.Idiom;

public class IdiomBuilder {

    private Integer id;
    private String name;

    public Idiom build() {
        return new Idiom(id, name);
    }

    public IdiomBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public IdiomBuilder setName(String name) {
        this.name = name;
        return this;
    }
}
