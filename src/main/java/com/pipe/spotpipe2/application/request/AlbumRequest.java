package com.pipe.spotpipe2.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.artists.Artist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AlbumRequest {

    @NotBlank
    private String title;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;

    private List<SongRequest> songs = new ArrayList<>();

    public Album toModel(Artist artist) {
        Album album = new Album(title, artist, releaseDate);
        songs.forEach(song -> album.addSong(song.toModel()));
        return album;
    }
}
