package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album albumAdded) {
        Artist artist = artistRepository.findById(albumAdded.getArtist().getId()).orElse(null);
        if(artist != null) {
            albumAdded.setArtist(artist);
            albumRepository.save(albumAdded);
            return albumAdded;
        } else {
            throw new EntityNotFoundException("");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{idAlbum}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Long idAlbum) {
        Album album = albumRepository.findById(idAlbum).orElse(null);
        if(album != null) {
            albumRepository.delete(album);
        } else {
            throw new EntityNotFoundException("L'album n'existe pas !");
        }
    }
}
