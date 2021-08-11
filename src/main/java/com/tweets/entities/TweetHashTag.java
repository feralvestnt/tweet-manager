package com.tweets.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@SequenceGenerator(name="TWEET_HASHTAG_ID_SEQ", sequenceName="TWEET_HASHTAG_ID_SEQ", allocationSize = 1)
@Table(name = "TWEET_HASHTAG")
public class TweetHashTag {

    @Id
    @GeneratedValue(generator = "TWEET_HASHTAG_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TWEET_FK", foreignKey = @ForeignKey(name = "FK_TWEET_HASHTAG"))
    private Tweet tweet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HASHTAG_FK", foreignKey = @ForeignKey(name = "FK_HASHTAG_TWEET"))
    private HashTag hashTag;
}
