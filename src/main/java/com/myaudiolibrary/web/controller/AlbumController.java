package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController
{

    @Autowired
    private AlbumRepository albumRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                              CREATION ALBUM                                                        //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping
    (
        method = RequestMethod.POST,
        produces = "application/json",
        consumes = "application/json",
        value = ""
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album album)
    {
        if (album.getTitle()==null || album.getTitle().equals("") )
        {
            throw new IllegalArgumentException("le titre ne doit pas etre vide");
        }

        if (albumRepository.findAlbumByTitleAndArtist_Id(album.getTitle(),album.getArtist().getId())!=null)
        {
            String message = "il y a deja un album avec ce titre dans le repertoire de l'artist "
                            +album.getArtist().getName();
            throw new EntityExistsException(message);
        }

        return albumRepository.save(album);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            SUPPRESSION ALBUM                                                       //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(method = RequestMethod.DELETE , value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT )//204
    public void deleteAlbum( @PathVariable(value = "id") Long id )
    {
        if(!albumRepository.existsById(id))
        {
            throw new EntityNotFoundException("L'album n'existe pas");
        }

        albumRepository.deleteById(id);
    }





}
