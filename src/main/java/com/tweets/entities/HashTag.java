package com.tweets.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="HASHTAG_ID_SEQ", sequenceName="HASHTAG_ID_SEQ", allocationSize = 1)
@Table(name = "HASHTAG")
public class HashTag {

    @Id
    @GeneratedValue(generator = "HASHTAG_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEXT")
    private String texto;
}
