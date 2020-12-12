package com.myaudiolibrary.web.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "artist")
public class Artist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private long id;

    @Column(name = "Name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "artist",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)//orphanRemoval = true
    @JsonIgnoreProperties(value = { "artist" })
//    @JsonIgnoreProperties("artist")
    private Set<Album> albums = new HashSet<>();


    public Artist()
    {

    }

    public long getId()
    {
        return id;
    }

    public void setId(long artistId)
    {
        this.id = artistId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Album> getAlbums()
    {
        return albums;
    }

    public void setAlbums(Set<Album> albums)
    {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Artist artist = (Artist) o;
        return id == artist.id && Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name);
    }

    @Override
    public String toString()
    {
        return "Artist{id=" + id + ", name='" + name + "\'}";
    }
}
