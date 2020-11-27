package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController
{


    @Autowired
    private ArtistRepository artistRepository;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            RECHERCHE ARTISTE PAR ID                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET,  produces = "application/json"  , value = "/{id}")
    public Artist artistInf(@PathVariable(value = "id") Long id)
    {
        Optional<Artist> optionalArtist = artistRepository.findById(id);
        if (optionalArtist.isEmpty())
        {
            throw new EntityNotFoundException("aucun artist id : " + id + " n'a été trouvé.");
        }

        Artist artist = optionalArtist.get();
        return artist;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            RECHERCHE ARTISTES PAR NOM                                              //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET,  produces = "application/json"  , value = "",params = {"name"})
    public Page<Artist> artistByName
    (
        @RequestParam("name") String name,
        @RequestParam("page") Integer page,
        @RequestParam("size") Integer size,
        @RequestParam("sortProperty") String sortProperty,
        @RequestParam(value = "sortDirection",defaultValue = "ASC")String sortDirection
    )
    {

        if(page <0 )
        {
            throw new IllegalArgumentException("le parametre page doit etre positif ou nul!");
        }
        if(size <=0 || size>100)
        {
            throw new IllegalArgumentException("le parametre page doit etre compris entre 0 et 100!");
        }
        if(!"ASC".equalsIgnoreCase(sortDirection)&&!"DESC".equalsIgnoreCase(sortDirection))
        {
            throw new IllegalArgumentException("le parametre page doit etre egal a ASC ou DESC");
        }

        PageRequest pageRequest = PageRequest.of(page,size,Sort.Direction.fromString(sortDirection),sortProperty);

//        Page<Artist> artistPage = artistRepository.findArtistLike(name,pageRequest);
        Page<Artist> artistPage = artistRepository.findArtistsByNameContains(name,pageRequest);

        if(page>artistPage.getTotalPages())
        {
            throw new IllegalArgumentException("le parametre page ne doit pas etre superieur au nombre total de page!");
        }

        return artistPage;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            RECHERCHE TOUS LES ARTISTE                                              //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET,  produces = "application/json"  , value = "")
    public Page<Artist> artistPage
    (
        @RequestParam("page") Integer page,
        @RequestParam("size") Integer size,
        @RequestParam("sortProperty") String sortProperty,
        @RequestParam(value = "sortDirection",defaultValue = "ASC")String sortDirection
    )
    {
        System.out.println("page actuelle : "+page);
        System.out.println("size : "+size);
        System.out.println("count : "+artistRepository.count());
        System.out.println("nombre de page total : "+artistRepository.count()/size);
        if(page <0 || (artistRepository.count()/size)<page)
        {
            throw new IllegalArgumentException
            (
                "le parametre page doit etre positif ou nul et ne doit pas etre superieur au nombre de page total!"
            );
        }
        if(size <=0 || size>100)
        {
            throw new IllegalArgumentException("le parametre page doit etre compris entre 0 et 100!");
        }
        if(!"ASC".equalsIgnoreCase(sortDirection)&&!"DESC".equalsIgnoreCase(sortDirection))
        {
            throw new IllegalArgumentException("le parametre page doit etre egal a ASC ou DESC");
        }

        PageRequest pageRequest = PageRequest.of(page,size,Sort.Direction.fromString(sortDirection),sortProperty);

        Page<Artist> artistPage = artistRepository.findAll(pageRequest);

        return artistPage;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            CREATION ARTISTE                                                        //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping
    (
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE ,
        consumes=MediaType.APPLICATION_JSON_VALUE ,
        value = ""
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist)
    {
        if (artistRepository.findByName(artist.getName())!=null)
        {
            throw new EntityExistsException("il y a deja un artist avec ce nom");
        }

        return artistRepository.save(artist);
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            MODIFICATION ARTISTE                                                    //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping
    (
        method = RequestMethod.PUT,
        produces = "application/json" ,
        consumes="application/json" ,
        value = "/{id}"
    )
    public Artist modifyArtist
    (
        @RequestBody Artist artist ,
        @PathVariable(value = "id") Long id
    )
    {
        if(artist.getId()!=id)
        {
            throw new IllegalArgumentException("les données envoyées ne correspondent pas à celles attendues");
        }
        if(!artistRepository.existsById(id))
        {
            throw new EntityNotFoundException("L'artiste n'existe pas");
        }

        artistRepository.save(artist);

        return artist;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            SUPPRESSION ARTISTE                                                     //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.DELETE , value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT )
    public void deleteArtist( @PathVariable(value = "id") Long id )
    {
        if(!artistRepository.existsById(id))
        {
            throw new EntityNotFoundException("L'artiste n'existe pas");
        }

        artistRepository.deleteById(id);
    }






}
