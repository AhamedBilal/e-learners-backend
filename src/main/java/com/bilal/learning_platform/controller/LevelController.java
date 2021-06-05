package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.model.Level;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LevelController {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelController(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @GetMapping
    public ResponseEntity<?> getLevels() {
        List<Level> all = levelRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addLevel(@RequestBody Level level) {
        levelRepository.save(level);
        return ResponseEntity.ok(new MessageResponse("Successfully Added!"));
    }

    @PutMapping
    public ResponseEntity<?> updateLevel(@RequestBody Level level) {
        levelRepository.save(level);
        return ResponseEntity.ok(new MessageResponse("Successfully Updated!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateLevel(@PathVariable Long id) {
        levelRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Successfully Deleted!"));
    }

}
