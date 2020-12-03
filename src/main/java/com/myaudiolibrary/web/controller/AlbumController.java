package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/albums")
public class AlbumController
{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    suppresion album                                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{idArtist}")
    public String newEmploye
    (
        final ModelMap model,
        @PathVariable(value = "idArtist") Long idArtist,
        @PathVariable(value = "idAlbum") Long idAlbum
    )
    {
        System.out.println("on supprime l'album");

        Artist artist = artistRepository.findById(idArtist).get();

        model.put("artist",artist);

        return "detailArtist";
    }



}
