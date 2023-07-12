package com.pipe.spotpipe2.domain.models.artists;

import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.songs.Song;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Album> albums = new HashSet<>();

    public Artist(String name) {
        this.name = name;
    }

    public void addAlbum(Album album) {
        this.getAlbums().add(album);
        album.setArtist(this);
    }
}
