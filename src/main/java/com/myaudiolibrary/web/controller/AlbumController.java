package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.exception.MyException;
import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.util.Optional;


@Controller
@RequestMapping(value = "/albums")
public class AlbumController
{

    @Autowired
    AlbumRepository albumRepository;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    ajout album                                                                     //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
    @RequestMapping
    (
        method = RequestMethod.POST,
        value = "",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView addAlbum(final ModelMap model, Album album)
    {
//        System.out.println("on ajoute l'album "+album.getTitle());
//        System.out.println("on recupere l'id "+album.getArtist().getId()+" et le nom de l'artist "+album.getArtist());

        if(album.getTitle().length() > 160)
        {
            throw new MyException("le titre de l'album comporte trop de caractères");
        }

        if(albumRepository.existsAlbumByTitleAndArtist_Id(album.getTitle(),album.getArtist().getId()))
        {
            throw new NonUniqueResultException("cet artiste possède déjà un album de ce titre");
        }

        albumRepository.save(album);

        model.put("artist",album.getArtist());

        return new RedirectView("/artists/"+album.getArtist().getId());
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    suppresion album                                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public RedirectView deleteAlbum
    (
        final ModelMap model,
        @PathVariable(value = "id") Long id
    )
    {

        Optional<Album> optionalAlbum = albumRepository.findById(id);
        if(optionalAlbum.isEmpty())
        {
            throw new EntityNotFoundException("l'album n'existe pas");
        }

        Artist artist = optionalAlbum.get().getArtist();

//        System.out.println("on supprime l'album" + id);
        albumRepository.deleteById(id);

        model.put("artist",artist);

        return new RedirectView("/artists/" + artist.getId());
    }



}
