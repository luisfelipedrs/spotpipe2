package com.pipe.spotpipe2.domain.services;

import com.pipe.spotpipe2.domain.models.albums.Album;
import com.pipe.spotpipe2.domain.models.albums.AlbumRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Transactional
    public Album save(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Transactional
    public void delete(Album album) {
        albumRepository.delete(album);
    }
}
