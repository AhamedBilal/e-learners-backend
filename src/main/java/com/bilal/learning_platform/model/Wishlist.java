package com.bilal.learning_platform.model;

import javax.persistence.*;

@Entity
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private User user;
    @ManyToOne()
    private Course course;
}
