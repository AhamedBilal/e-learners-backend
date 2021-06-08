package com.bilal.learning_platform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    @ManyToOne()
    private Course course;
    @ManyToOne
    private Order orderId;
}
