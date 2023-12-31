package com.pipe.spotpipe2.domain.models.albums;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pipe.spotpipe2.domain.models.artists.Artist;
import com.pipe.spotpipe2.domain.models.songs.Song;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @JsonBackReference
    @ManyToOne
    private Artist artist;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    public Album(String title, Artist artist, LocalDate releaseDate) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }

    public void addSong(Song song) {
        this.getSongs().add(song);
        this.artist.addSong(song);
        song.setArtist(this.artist);
        song.setAlbum(this);
    }

    public void removeSong(Song song) {
        this.getSongs().remove(song);
        this.artist.removeSong(song);
    }
}
