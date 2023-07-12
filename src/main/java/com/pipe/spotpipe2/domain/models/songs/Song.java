package com.pipe.spotpipe2.domain.models.songs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.artists.Artist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @JsonBackReference
    @ManyToOne
    private Artist artist;

    @JsonBackReference
    @ManyToOne
    private Album album;

    public Song(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
}
