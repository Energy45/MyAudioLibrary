package com.myaudiolibrary.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Long id;

    @Column(name = "Name")
    private String name;

    @JsonIgnoreProperties("artist")
    @OneToMany(mappedBy = "artist")
    private Set<Album> albums = new HashSet<>();

    public void setArtistId(Long albumId) {
        this.id = albumId;
    }

    public Long getArtistId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(HashSet<Album> albums) {
        this.albums = albums;
    }
}
