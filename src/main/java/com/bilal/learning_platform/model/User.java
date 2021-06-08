package com.bilal.learning_platform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String fname;
    private String lname;
    private String headline;
    @Column(length = 5000)
    private String bio;
    private String website;
    private String twitter;
    private String profilePic;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToMany
    private final List<CourseData> course = new ArrayList<>();


    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String fname, String lname, String headline, String bio, String website, String twitter) {
        this.fname = fname;
        this.lname = lname;
        this.headline = headline;
        this.bio = bio;
        this.website = website;
        this.twitter = twitter;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", headline='" + headline + '\'' +
                ", bio='" + bio + '\'' +
                ", website='" + website + '\'' +
                ", twitter='" + twitter + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", role=" + role +
                ", course=" + course +
                '}';
    }
}