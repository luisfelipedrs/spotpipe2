package com.pipe.spotpipe2.application.response;

import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.artists.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ArtistResponse {

    private Long id;
    private String name;
    private List<String> albums;

    public ArtistResponse(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.albums = artist.getAlbums()
                .stream()
                .map(Album::getTitle)
                .collect(Collectors.toList());
    }
}
