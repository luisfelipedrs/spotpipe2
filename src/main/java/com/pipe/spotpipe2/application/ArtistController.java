package com.pipe.spotpipe2.application;

import com.pipe.spotpipe2.application.request.ArtistRequest;
import com.pipe.spotpipe2.application.request.UserRequest;
import com.pipe.spotpipe2.application.response.ArtistResponse;
import com.pipe.spotpipe2.application.response.UserResponse;
import com.pipe.spotpipe2.domain.services.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    public ResponseEntity<?> saveArtist(@RequestBody @Valid ArtistRequest artistRequest,
                                        UriComponentsBuilder uriBuilder) {

        if (artistService.existsByName(artistRequest.getName())) {
            return ResponseEntity.badRequest().build();
        }

        final var artist = artistService.save(artistRequest.toModel());
        URI location = uriBuilder.path("/artists/{id}")
                .buildAndExpand(artist.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllUsers() {
        return ResponseEntity.ok(artistService.findAll()
                .stream()
                .map(ArtistResponse::new)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new ArtistResponse(artistService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        final var userModel = artistService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        artistService.delete(userModel);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid ArtistRequest artistRequest) {
        final var artist = artistService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        artist.setName(artist.getName());

        return ResponseEntity.ok(artistService.save(artist));
    }
}
