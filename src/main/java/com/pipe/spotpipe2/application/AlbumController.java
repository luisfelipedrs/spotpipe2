package com.pipe.spotpipe2.application;

import com.pipe.spotpipe2.application.request.AlbumRequest;
import com.pipe.spotpipe2.application.response.AlbumResponse;
import com.pipe.spotpipe2.domain.services.AlbumService;
import com.pipe.spotpipe2.domain.services.ArtistService;
import com.pipe.spotpipe2.infra.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AlbumController {

    private final AlbumService albumService;
    private final ArtistService artistService;

    public AlbumController(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @Transactional
    @PostMapping("/artists/{id}/albums")
    public ResponseEntity<?> saveAlbumToArtist(@PathVariable Long id,
                                             @RequestBody @Valid AlbumRequest albumRequest,
                                             UriComponentsBuilder uriBuilder) {

        var artist = artistService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        var album = albumRequest.toModel(artist);
        artist.addAlbum(album);
        artistService.save(artist);

        URI location = uriBuilder.path("/artists/{id}/albums/{albumId}")
                .buildAndExpand(artist.getId(), album.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {

        return ResponseEntity.ok(albumService.findAll()
                .stream()
                .map(AlbumResponse::new)
                .toList());
    }

    @Transactional
    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumResponse> getAlbumById(@PathVariable Long id) {

        return ResponseEntity.ok(new AlbumResponse(albumService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id))));
    }

    @Transactional
    @GetMapping("/artists/{artistId}/albums")
    public ResponseEntity<List<AlbumResponse>> getAlbunsFromArtist(@PathVariable("artistId") Long id) {

        var artist = artistService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return ResponseEntity.ok(artist.getAlbums().
                stream().
                map(AlbumResponse::new)
                .toList());
    }

    @Transactional
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {

        final var album = albumService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        album.getArtist().removeAlbum(album);

        albumService.delete(album);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/albums/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long id,
                                         @RequestBody @Valid AlbumRequest albumRequest) {

        final var album = albumService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        album.setTitle(albumRequest.getTitle());
        album.setReleaseDate(albumRequest.getReleaseDate());

        albumService.save(album);
        return ResponseEntity.ok().build();
    }
}
