package com.codegym.musichouse.controller;

import com.codegym.musichouse.message.respond.ResponseMessage;
import com.codegym.musichouse.model.Song;
import com.codegym.musichouse.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 21600000)
@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createSong(@Valid @RequestBody Song songRequest) {
//        Song song = new Song(
//                songRequest.getNameSong(),
//                songRequest.getSinger(),
//                songRequest.getCategory(),
//                songRequest.getLyrics(),
//                songRequest.getAvatarUrl(),
//                songRequest.getMp3Url(),
//                songRequest.getLikeSong(),
//                songRequest.getListenSong(),
//                songRequest.getDescribes()
//
//        );
        songService.save(songRequest);
        return new ResponseEntity<>(new ResponseMessage("Create Song successfully!",null), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAllSong() {
        List<Song> songs = this.songService.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSong(@PathVariable("id") Long id) {
        Song song = songService.findByIdSong(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
//        try {
//            Song song = songService
//                    .findById(id)
//                    .orElseThrow(EntityNotFoundException::new);
//            song.setListenSong(song.getListenSong()+ 1);
//            songService.save(song);
//            return new ResponseEntity<>(song, HttpStatus.OK);
//        } catch (EntityNotFoundException e){
//            return new ResponseEntity<>(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
//        }
    }


    @DeleteMapping("/by/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSongById(@PathVariable("id") Long id) {
        songService.delete(id);
        return new ResponseEntity<>(new ResponseMessage("Delete Song successfully!"), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateSong(@RequestBody Song song, @PathVariable("id") Long id){
        Song song1 = songService.findByIdSong(id);

        song1.setAvatarUrl(song.getAvatarUrl());
        song1.setCategory(song.getCategory());
        song1.setDescribes(song.getDescribes());
        song1.setNameSong(song.getNameSong());
        song1.setSinger(song.getSinger());
        song1.setMp3Url(song.getMp3Url());
        song1.setLyrics(song.getLyrics());
        song1.setLikeSong(song.getLikeSong());
        song1.setListenSong(song.getListenSong());

        songService.save(song1);
        return new ResponseEntity<>(new ResponseMessage("update song successfully!",null), HttpStatus.OK);
    }

}
