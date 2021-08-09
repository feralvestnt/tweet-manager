package com.tweets.builder;

import com.tweets.entities.Idiom;
import com.tweets.entities.Tweet;
import com.tweets.entities.User;

import java.util.List;

public class TweetBuilder {

    private Integer id;
    private User user;
    private String texto;
    private Idiom idiom;
    private List<String> hashtag;
    private Boolean valid;

    public Tweet build() {
        return new Tweet(id, user, texto, idiom, hashtag, valid);
    }

    public TweetBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TweetBuilder setUser(User usuario) {
        this.user = usuario;
        return this;
    }

    public TweetBuilder setTexto(String texto) {
        this.texto = texto;
        return this;
    }

    public TweetBuilder setIdiom(Idiom idiom) {
        this.idiom = idiom;
        return this;
    }

    public TweetBuilder setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
        return this;
    }

    public TweetBuilder setValid(Boolean valid) {
        this.valid = valid;
        return this;
    }
}
