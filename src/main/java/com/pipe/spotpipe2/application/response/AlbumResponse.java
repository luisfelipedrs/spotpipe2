package com.pipe.spotpipe2.application.response;

import com.pipe.spotpipe2.domain.models.albums.Album;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class AlbumResponse {

    private String title;
    private String artist;
    private Set<SongResponse> songs = new HashSet<>();

    public AlbumResponse(Album album) {
        this.title = album.getTitle();
        this.artist = album.getArtist().getName();
    }
}
