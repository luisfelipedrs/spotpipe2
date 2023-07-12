package com.pipe.spotpipe2.application;

import com.pipe.spotpipe2.application.request.ArtistRequest;
import com.pipe.spotpipe2.application.response.ArtistResponse;
import com.pipe.spotpipe2.domain.services.ArtistService;
import com.pipe.spotpipe2.infra.exceptions.ResourceAlreadyExistsException;
import com.pipe.spotpipe2.infra.exceptions.ResourceNotFoundException;
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
            throw new ResourceAlreadyExistsException(artistRequest.getName());
        }

        final var artist = artistService.save(artistRequest.toModel());
        URI location = uriBuilder.path("/artists/{id}")
                .buildAndExpand(artist.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {

        return ResponseEntity.ok(artistService.findAll()
                .stream()
                .map(ArtistResponse::new)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {

        return ResponseEntity.ok(new ArtistResponse(artistService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {

        final var userModel = artistService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        artistService.delete(userModel);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(@PathVariable Long id,
                                          @RequestBody @Valid ArtistRequest artistRequest) {

        final var artist = artistService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        artist.setName(artistRequest.getName());

        artistService.save(artist);
        return ResponseEntity.ok().build();
    }
}
