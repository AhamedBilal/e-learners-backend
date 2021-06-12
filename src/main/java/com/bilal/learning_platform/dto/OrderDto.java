package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Order;
import com.bilal.learning_platform.model.OrderDetails;
import com.bilal.learning_platform.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private BigDecimal total;
    private Date createdAt;
    private Long userId;
    private String username;
    private List<OrderDetailDto> orderDetails = new ArrayList<>();

    public OrderDto(Order order) {
        this.id = order.getId();
        this.total = order.getTotal();
        this.createdAt = order.getCreatedAt();
        this.userId = order.getUser().getId();
        this.username = order.getUser().getUsername();
        this.orderDetails = order.getOrderDetails().stream().map(OrderDetailDto::new).collect(Collectors.toList());
    }
}
