package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafAudioController {

    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(final ModelMap model) {
        return "accueil";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists/{artistId}")
    public String getArtistById(final ModelMap model, @PathVariable Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElse(null);
        if(artist != null) {
            model.put("nameArtist", artist.getName());
            model.put("albums", artist.getAlbums());
        }
        return "detailArtist";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists", params = "name")
    public String getArtistByName(final ModelMap model, @RequestParam String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "name") String sortProperty, @RequestParam(defaultValue = "ASC") String sortDirection) {
        Page<Artist> artists = artistRepository.findByName(name, PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
        if(!artists.isEmpty()) {
            model.put("artists", artists);
            model.put("start", (page) * size + 1);
            model.put("end", (page) * size + artists.getNumberOfElements());
            model.put("totalArtist", artists.getTotalElements());
        } else {
            model.put("start", 0);
            model.put("end", 0);
            model.put("totalArtist", 0);
        }
        return "listeArtists";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists")
    public String getArtistPage(final ModelMap model, @RequestParam Integer page, @RequestParam Integer size, @RequestParam String sortProperty, @RequestParam String sortDirection) {
        Page<Artist> artists = artistRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
        if(!artists.isEmpty()) {
            model.put("artists", artists);
            model.put("start", (page) * size + 1);
            model.put("end", (page) * size + artists.getNumberOfElements());
            model.put("totalArtist", artists.getTotalElements());
        } else {
            model.put("start", 0);
            model.put("end", 0);
            model.put("totalArtist", 0);
        }

        return "listeArtists";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/artists")
}
