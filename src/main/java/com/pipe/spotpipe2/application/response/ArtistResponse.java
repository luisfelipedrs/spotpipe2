package com.pipe.spotpipe2.application.response;

import com.pipe.spotpipe2.domain.models.artists.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArtistResponse {

    private String name;

    public ArtistResponse(Artist artist) {
        this.name = artist.getName();
    }
}
