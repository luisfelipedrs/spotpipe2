package com.pipe.spotpipe2.domain.services;

import com.pipe.spotpipe2.domain.models.songs.Song;
import com.pipe.spotpipe2.domain.models.songs.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Transactional
    public Song save(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Transactional
    public void delete(Song song) {
        songRepository.delete(song);
    }
}
