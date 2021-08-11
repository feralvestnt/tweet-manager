package com.tweets.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SequenceGenerator(name="ACCESS_TOKEN_ID_SEQ", sequenceName="ACCESS_TOKEN_ID_SEQ", allocationSize = 1)
@Table(name = "ACCESS_TOKEN")
public class AccessAuth {

    @Id
    @GeneratedValue(generator = "ACCESS_TOKEN_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "TOKEN_SECRET")
    private String tokenSecret;
}
