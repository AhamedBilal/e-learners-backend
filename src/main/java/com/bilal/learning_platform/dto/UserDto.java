package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.CourseData;
import com.bilal.learning_platform.model.Role;
import com.bilal.learning_platform.model.User;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fname;
    private String lname;
    private String headline;
    private String bio;

    public UserDto(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.fname = u.getFname();
        this.lname = u.getLname();
        this.headline = u.getHeadline();
        this.bio = u.getBio();
        this.website = u.getWebsite();
        this.twitter = u.getTwitter();
        this.role = u.getRole().getName().name();
    }

    public UserDto(Long id, String username, String email, String fname, String lname, String headline, String bio, String website, String twitter, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.headline = headline;
        this.bio = bio;
        this.website = website;
        this.twitter = twitter;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<CourseData> getCourse() {
        return course;
    }

    private String website;
    private String twitter;
    private String role;
    private final List<CourseData> course = new ArrayList<>();

    public UserDto() {
    }
}
