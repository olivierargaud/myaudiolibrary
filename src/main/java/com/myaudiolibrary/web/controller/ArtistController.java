package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Controller
@RequestMapping(value = "/artists")
public class ArtistController
{

    @Autowired
    ArtistRepository artistRepository;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                                    //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detailArtist(final ModelMap model , @PathVariable(value = "id") Long id)
    {
        Optional<Artist> artistOptional =  artistRepository.findById(id);
        if (artistOptional.isEmpty())
        {
            System.out.println("lancer une exception");
        }

        Artist artist = artistOptional.get();
        model.put("emp",artist);

        return "detailArtist";

    }





}
