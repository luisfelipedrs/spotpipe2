package com.pipe.spotpipe2.application.response;

import com.pipe.spotpipe2.domain.models.songs.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SongResponse {

    private Long id;
    private String title;
    private String genre;
    private String artist;
    private String album;

    public SongResponse(Song song) {
        this.id = song.getId();
        this.title = song.getTitle();
        this.genre = song.getGenre();
        this.artist = song.getArtist().getName();
        this.album = song.getAlbum().getTitle();
    }
}
