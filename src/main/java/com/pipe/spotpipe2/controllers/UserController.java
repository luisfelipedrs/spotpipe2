package com.pipe.spotpipe2.controllers;

import com.pipe.spotpipe2.controllers.request.UserRequest;
import com.pipe.spotpipe2.controllers.response.UserResponse;
import com.pipe.spotpipe2.models.UserModel;
import com.pipe.spotpipe2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserRequest userRequest,
                                      UriComponentsBuilder uriBuilder) {

        final var user = userService.save(userRequest.toModel());
        URI location = uriBuilder.path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(UserResponse::new)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new UserResponse(userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        final var userModel = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        userService.delete(userModel);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid UserRequest userRequest) {
        final var userModel = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        userModel.setName(userRequest.getName());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(userModel.getPassword());
        userModel.setBirthDate(userRequest.getBirthDate());

        return ResponseEntity.ok(userService.save(userModel));
    }
}
