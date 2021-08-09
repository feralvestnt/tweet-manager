package com.tweets.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="IDIOM_ID_SEQ", sequenceName="IDIOM_ID_SEQ", allocationSize = 1)
@Table(name = "IDIOM")
public class Idiom {

    @Id
    @GeneratedValue(generator = "IDIOM_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;
}
