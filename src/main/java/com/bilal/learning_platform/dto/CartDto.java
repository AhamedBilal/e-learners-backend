package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Cart;
import com.bilal.learning_platform.model.Course;
import com.bilal.learning_platform.model.User;
import com.bilal.learning_platform.payload.response.CourseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Long userid;
    private List<CourseResponse> courses = new ArrayList<>();

    public CartDto(Cart cart) {
        this.id = cart.getId();
        this.userid = cart.getUser().getId();
        this.courses = cart.getCourses().stream().map(CourseResponse::new).collect(Collectors.toList());
    }
}