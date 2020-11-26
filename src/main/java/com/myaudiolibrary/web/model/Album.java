package com.myaudiolibrary.web.model;

import javax.persistence.*;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer id;

    @Column(name = "Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "artistId", nullable = false)
    private Artist artist;

    public Integer getAlbumId() {
        return id;
    }

    public void setAlbumId(Integer albumId) {
        this.id = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
