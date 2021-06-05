package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.model.Category;
import com.bilal.learning_platform.model.Level;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping
    public ResponseEntity<?> getCategories() {
        List<Category> all = categoryRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok(new MessageResponse("Successfully Added!"));
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody  Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok(new MessageResponse("Successfully Updated!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Successfully Deleted!"));
    }
}
