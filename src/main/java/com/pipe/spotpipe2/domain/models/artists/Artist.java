package com.pipe.spotpipe2.domain.models.artists;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    @JsonManagedReference
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Set<Album> albums = new HashSet<>();

    public Artist(String name) {
        this.name = name;
    }

    public void addAlbum(Album album) {
        this.getAlbums().add(album);
    }

    public void addSong(Song song) {
        this.getSongs().add(song);
    }

    public void removeAlbum(Album album) {
        this.getAlbums().remove(album);
    }

    public void removeSong(Song song) {
        this.getSongs().remove(song);
    }
}
