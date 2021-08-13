package com.tweets.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="USER_ID_SEQ", sequenceName="USER_ID_SEQ", allocationSize = 1)
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(generator = "USER_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "FOLLOWERS_COUNT")
    private Long followersCount;
}
