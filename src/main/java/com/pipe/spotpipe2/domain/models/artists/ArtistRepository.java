package com.pipe.spotpipe2.domain.models.artists;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    boolean existsByName(String name);
}
