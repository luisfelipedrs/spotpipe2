package com.pipe.spotpipe2.application.response;

import com.pipe.spotpipe2.domain.models.artists.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ArtistResponse {

    private Long id;
    private String name;
    private List<AlbumResponse> albums;

    public ArtistResponse(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.albums = artist.getAlbums()
                .stream()
                .map(AlbumResponse::new)
                .toList();
    }
}
