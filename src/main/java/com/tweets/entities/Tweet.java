package com.tweets.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="TWEET_ID_SEQ", sequenceName="TWEET_ID_SEQ", allocationSize = 1)
@Table(name = "TWEET")
public class Tweet {

    @Id
    @GeneratedValue(generator = "TWEET_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_USER", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_TWEET"))
    private User user;

    @Column(name = "TEXT")
    private String texto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_IDIOM", nullable = false, foreignKey = @ForeignKey(name = "FK_IDIOM_TWEET"))
    private Idiom idiom;

    @Transient
    private List<String> hashtag;

    @Column(name = "VALID")
    private Boolean valid;
}
