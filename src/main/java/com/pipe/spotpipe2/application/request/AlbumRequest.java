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

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AlbumRequest {

    @NotBlank
    private String title;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;

    public Album toModel(Artist artist) {
        return new Album(title, artist, releaseDate);
    }
}