package com.pipe.spotpipe2.application.request;

import com.pipe.spotpipe2.domain.models.artists.Artist;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArtistRequest {

    @NotBlank
    private String name;

    public Artist toModel() {
        return new Artist(name);
    }
}
