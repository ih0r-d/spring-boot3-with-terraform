package com.demo.simplerestapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.javaclub.clients.model.User;
import ua.lviv.javaclub.clients.model.UserRequest;
import ua.lviv.javaclub.clients.repository.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class RestUserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> users(){
        var users = userRepository.users();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createUser(@RequestBody UserRequest userRequest){
        var userId = userRepository.createUser(userRequest);
        return ResponseEntity.ok(Map.of("id", userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> byId(@PathVariable Long id){
        var userOptional = userRepository.getById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestParam Long id, @RequestBody UserRequest userRequest){
        var userOptional = userRepository.updateUserById(id, userRequest);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> updateUser(@PathVariable Long id){
        var userOptional = userRepository.removeUserById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
