package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    private final String ERROR_ARTIST_NOT_FOUND = "L'artiste demandé n'existe pas !";
    private final String ERROR_NAME_EMPTY = "Le nom passé en paramètre est vide !";

    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idArtist}")
    public Artist getArtiste(@PathVariable(value = "idArtist") Long idArtist) {
        Artist artist = artistRepository.findById(idArtist).orElse(null);
        if(artist != null) {
            return artist;
        } else {
            throw new EntityNotFoundException(ERROR_ARTIST_NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public Page<Artist> getArtisteByName(@RequestParam String name,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size,
                                         @RequestParam String sortDirection,
                                         @RequestParam String sortProperty) {
        return artistRepository.findByName(name, PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortProperty));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Artist> getListArtist(@RequestParam Integer page,
                                         @RequestParam Integer size,
                                         @RequestParam String sortDirection,
                                         @RequestParam String sortProperty) {
        return artistRepository.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortProperty));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist createArtist(@RequestBody Artist artistCreated) {
        artistRepository.save(artistCreated);
        return artistCreated;
    }
}
