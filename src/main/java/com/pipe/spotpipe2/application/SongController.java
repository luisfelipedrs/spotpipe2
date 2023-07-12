package com.pipe.spotpipe2.application;

import com.pipe.spotpipe2.application.request.SongRequest;
import com.pipe.spotpipe2.application.response.SongResponse;
import com.pipe.spotpipe2.domain.models.songs.Song;
import com.pipe.spotpipe2.domain.services.AlbumService;
import com.pipe.spotpipe2.domain.services.ArtistService;
import com.pipe.spotpipe2.domain.services.SongService;
import com.pipe.spotpipe2.infra.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class SongController {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;

    public SongController(ArtistService artistService, AlbumService albumService, SongService songService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @PostMapping("/artists/{artistId}/albums/{albumId}/songs")
    public ResponseEntity<?> saveSong(@PathVariable("artistId") Long artistId,
                                      @PathVariable("albumId") Long albumId,
                                      @RequestBody @Valid SongRequest songRequest,
                                      UriComponentsBuilder uriBuilder) {

        var artist = artistService.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException(artistId));

        var album = albumService.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException(albumId));

        var song = songRequest.toModel();

        album.addSong(song);
        songService.save(song);

        URI location = uriBuilder.path("/artists/{artistId}/albums/{albumId}/songs")
                .buildAndExpand(artist.getId(), album.getId(), song.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongResponse>> getAllSongs() {

        return ResponseEntity.ok(songService.getAllSongs()
                .stream()
                .map(SongResponse::new)
                .toList());
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {

        return ResponseEntity.ok(new SongResponse(songService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id))));
    }

    @GetMapping("/albums/{id}/songs")
    public ResponseEntity<List<SongResponse>> getAllSongsAlbum(@PathVariable Long id) {

        var album = albumService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return ResponseEntity.ok(album.getSongs().stream().map(SongResponse::new).toList());
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {

        var song = songService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        song.getAlbum().removeSong(song);

        songService.delete(song);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id,
                                        @RequestBody @Valid SongRequest songRequest) {

        var song = songService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        song.setTitle(songRequest.getTitle());
        song.setGenre(songRequest.getGenre());

        songService.save(song);
        return ResponseEntity.ok().build();
    }
}
