package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CartDto;
import com.bilal.learning_platform.model.Cart;
import com.bilal.learning_platform.model.Course;
import com.bilal.learning_platform.model.User;
import com.bilal.learning_platform.payload.request.CartRequest;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.CartRepository;
import com.bilal.learning_platform.repository.CourseRepository;
import com.bilal.learning_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartController(CartRepository cartRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(cartRequest.getId()).orElseThrow(() -> new RuntimeException("Course Not found"));
        Cart cart = user.getCart();
        if (cart == null) cart = new Cart();
        cart.setUser(user);
        if (!cart.getCourses().contains(course)) {
            cart.getCourses().add(course);
            Cart save = cartRepository.save(cart);
        }
        return ResponseEntity.ok(new MessageResponse("Added to Cart"));
    }

    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart != null) {
            return ResponseEntity.ok(new CartDto(cart));
        }
        return ResponseEntity.ok(new MessageResponse("No Cart yet"));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("courseId") Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart != null) {
            Course course1 = cart.getCourses().stream().filter(course -> course.getId() == id).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));
            cart.getCourses().remove(course1);
            cartRepository.save(cart);
            return ResponseEntity.ok(new CartDto(cart));
        }
        return ResponseEntity.ok(new MessageResponse("No Cart yet"));
    }

}
