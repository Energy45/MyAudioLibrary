package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafAudioController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(final ModelMap model) {
        return "accueil";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists/{artistId}")
    public String getArtistById(final ModelMap model, @PathVariable Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElse(null);
        if(artist != null) {
            model.put("artist", artist);
        }
        return "detailArtist";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists", params = "name")
    public String getArtistByName(final ModelMap model, @RequestParam String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "name") String sortProperty, @RequestParam(defaultValue = "ASC") String sortDirection) {
        Page<Artist> artists = artistRepository.findByNameContains(name, PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
        if(!artists.isEmpty()) {
            model.put("artists", artists);
            model.put("start", (page) * size + 1);
            model.put("end", (page) * size + artists.getNumberOfElements());
            model.put("totalArtist", artists.getTotalElements());
            model.put("previousPage", page - 1);
            model.put("currentPage", page);
            model.put("nextPage", page + 1);
            model.put("isLastPage", artists.isLast());
        } else {
            model.put("start", 0);
            model.put("end", 0);
            model.put("totalArtist", 0);
            model.put("previousPage", 0);
            model.put("currentPage", 0);
            model.put("nextPage", 0);
            model.put("isLastPage", true);
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
            model.put("previousPage", page - 1);
            model.put("currentPage", page);
            model.put("nextPage", page + 1);
            model.put("isLastPage", artists.isLast());
        } else {
            model.put("start", 0);
            model.put("end", 0);
            model.put("totalArtist", 0);
            model.put("previousPage", 0);
            model.put("currentPage", 0);
            model.put("nextPage", 0);
            model.put("isLastPage", true);
        }

        return "listeArtists";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists/new")
    public String getPageCreationArtist(final ModelMap model) {
        model.put("artist", new Artist());

        return "detailArtist";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/artists")
    public String saveArtist(final ModelMap model, Artist artistCreated) {
        //Check if exist
        artistRepository.save(artistCreated);
        model.put("artist", artistCreated);
        return "detailArtist";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists/{idArtist}/delete")
    public RedirectView deleteArtist(@PathVariable Long idArtist) {
        Artist artist = artistRepository.findById(idArtist).orElse(null);
        if(artist != null) {
            artistRepository.delete(artist);
            return new RedirectView("/thymeleaf/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
        } else {
            //404
            return new RedirectView("/thymeleaf");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/artists/{idArtist}/album")
    public String createAlbum(final ModelMap model, @PathVariable Long idArtist, Album album) {
        Artist artist = artistRepository.findById(idArtist).orElse(null);
        if(artist != null) {
            album.setArtist(artist);
            albumRepository.save(album);
            model.put("artist", artist);
        }

        return "detailArtist";
    }
}
