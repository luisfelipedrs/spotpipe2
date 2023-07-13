package com.pipe.spotpipe2.domain.services;

import com.pipe.spotpipe2.domain.models.artists.Artist;
import com.pipe.spotpipe2.domain.models.artists.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id);
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public void delete(Artist artist) {
        artistRepository.delete(artist);
    }

    public boolean existsByName(String name) {
        return artistRepository.existsByName(name);
    }
}
