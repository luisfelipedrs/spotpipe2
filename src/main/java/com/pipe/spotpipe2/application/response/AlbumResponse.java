package com.pipe.spotpipe2.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.songs.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class AlbumResponse {

    private Long id;
    private String title;
    private String artist;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;

    private List<String> songs = new ArrayList<>();

    public AlbumResponse(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.artist = album.getArtist().getName();
        this.releaseDate = album.getReleaseDate();
        this.songs = album.getSongs()
                 .stream()
                 .map(Song::getTitle)
                 .toList();
    }
}
