package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CartDto;
import com.bilal.learning_platform.dto.OrderDto;
import com.bilal.learning_platform.model.*;
import com.bilal.learning_platform.payload.request.OrderRequest;
import com.bilal.learning_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
@Transactional
public class OrderController {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LevelRepository levelRepository;
    private final CourseDataRepository courseDataRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderController(UserRepository userRepository, CategoryRepository categoryRepository, LevelRepository levelRepository, CourseDataRepository courseDataRepository, InstructorRepository instructorRepository, CourseRepository courseRepository, CartRepository cartRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.levelRepository = levelRepository;
        this.courseDataRepository = courseDataRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }


    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest request, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        List<Course> courses = cart.getCourses();
        if (courses.size() > 0) {
            Order order = new Order();
            BigDecimal total = courses.stream().map(course -> course.getPrice()).reduce(BigDecimal.ZERO, (subtotal, course2) -> subtotal.add(course2));
            order.setUser(user);
            order.setTotal(total);
            Order save = orderRepository.save(order);
            System.out.println(save);
            courses.forEach(course -> {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(save);
                orderDetails.setPrice(course.getPrice());
                orderDetails.setCourse(course);
                orderDetailsRepository.save(orderDetails);
                // increment course students
                course.setStudents(course.getStudents() + 1);
                courseRepository.save(course);

                // increment category students
                Category category = course.getCategory();
                category.setStudents(category.getStudents() + 1);
                categoryRepository.save(category);

                // increment level students
                Instructor instructor = course.getInstructor();
                instructor.setStudents(instructor.getStudents() + 1);
                instructorRepository.save(instructor);

                // add to learning
                CourseData courseData = new CourseData();
                courseData.setCourse(course);
                courseData.setUser(user);
                courseData.setStatus(EStatus.ONGOING.name());
                courseDataRepository.save(courseData);
            });
            cart.getCourses().clear();
            Cart save1 = cartRepository.save(cart);
            return ResponseEntity.ok(new CartDto(save1));
        } else {
            return ResponseEntity.badRequest().body("No items");
        }

    }

    @GetMapping ("/user")
    public ResponseEntity<?> getPurcheseHisoryByUserId(Authentication authentication) {
        List<OrderDto> collect = orderRepository.findAllByUser_Username(authentication.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

}
