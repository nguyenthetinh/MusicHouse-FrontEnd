package com.codegym.musichouse.repository;

import com.codegym.musichouse.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByUserId (Long userId);
    Optional<Song> findByNameSongContaining(String song);
}
