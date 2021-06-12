package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Course;
import com.bilal.learning_platform.model.Order;
import com.bilal.learning_platform.model.OrderDetails;
import com.bilal.learning_platform.payload.response.CourseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private BigDecimal price;
    private CourseResponse course;
    private Long orderId;

    public OrderDetailDto(OrderDetails details) {
        this.id = details.getId();
        this.price = details.getPrice();
        this.course = new CourseResponse(details.getCourse());
        this.orderId = details.getOrderId().getId();
    }
}
