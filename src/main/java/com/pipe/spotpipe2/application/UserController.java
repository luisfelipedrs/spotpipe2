package com.pipe.spotpipe2.application;

import com.pipe.spotpipe2.application.request.UserRequest;
import com.pipe.spotpipe2.application.response.UserResponse;
import com.pipe.spotpipe2.domain.services.UserService;
import com.pipe.spotpipe2.infra.exceptions.ResourceAlreadyExistsException;
import com.pipe.spotpipe2.infra.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

        if (userService.existsByEmail(userRequest.getEmail())) {
            throw new ResourceAlreadyExistsException(userRequest.getEmail());
        }

        if (userService.existsByUsername(userRequest.getUsername())) {
            throw new ResourceAlreadyExistsException(userRequest.getUsername());
        }

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
                .orElseThrow(() -> new ResourceNotFoundException(id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        final var userModel = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        userService.delete(userModel);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid UserRequest userRequest) {

        final var userModel = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        userModel.setUsername(userRequest.getUsername());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(userModel.getPassword());
        userModel.setBirthDate(userRequest.getBirthDate());

        return ResponseEntity.ok(userService.save(userModel));
    }
}
