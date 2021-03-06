package com.tweets.builder;

import com.tweets.entities.User;

public class UserBuilder {

    private Integer id;
    private String name;
    private String location;
    private Long followersCount;

    public User build() {
        return new User(id, name, location, followersCount);
    }

    public UserBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public UserBuilder setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
        return this;
    }
}
