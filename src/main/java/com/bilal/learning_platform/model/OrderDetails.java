package com.bilal.learning_platform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne()
    private Course course;
    @ManyToOne
    private Order orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Order getOrder() {
        return orderId;
    }

    public void setOrder(Order order) {
        this.orderId = order;
    }
}
