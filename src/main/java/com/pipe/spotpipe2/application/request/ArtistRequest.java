package com.pipe.spotpipe2.application.request;

import com.pipe.spotpipe2.domain.models.artists.Artist;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArtistRequest {

    @NotBlank
    private String name;

    private List<AlbumRequest> albums = new ArrayList<>();

    public Artist toModel() {
        return new Artist(name);
    }
}
