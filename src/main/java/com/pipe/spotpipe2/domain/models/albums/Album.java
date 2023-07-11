package com.pipe.spotpipe2.domain.models.albums;

import com.pipe.spotpipe2.domain.models.artists.Artist;
import com.pipe.spotpipe2.domain.models.songs.Song;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne
    private Artist artist;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    public Album(String title, Artist artist, LocalDate releaseDate) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }
}
