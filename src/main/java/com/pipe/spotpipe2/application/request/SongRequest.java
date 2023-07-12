package com.pipe.spotpipe2.application.request;

import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.songs.Song;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SongRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    public Song toModel() {
        return new Song(title, genre);
    }
}
