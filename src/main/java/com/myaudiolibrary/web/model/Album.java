package com.myaudiolibrary.web.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "album")
public class Album
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId",nullable = false)
    private Long id;

    @Column(name = "Title",nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn( name = "ArtistId" ,nullable = false)
    private Artist artist;

    public Album()
    {

    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long albumId)
    {
        this.id = albumId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public void setArtist(Artist artist)
    {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Album album = (Album) o;
        return Objects.equals(id, album.id) &&
                Objects.equals(title, album.title) &&
                Objects.equals(artist, album.artist);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, title, artist);
    }

    @Override
    public String toString()
    {
        return "Album{" +
                "albumId=" + id +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                '}';
    }
}