package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.exception.MyException;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.util.Optional;


@Controller
@RequestMapping(value = "/artists")
public class ArtistController
{

    @Autowired
    ArtistRepository artistRepository;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                          detail artiste                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detailArtist(final ModelMap model , @PathVariable(value = "id") Long id)
    {
        Optional<Artist> artistOptional =  artistRepository.findById(id);
        if (artistOptional.isEmpty())
        {
//            System.out.println("lancer une exception");
            throw new EntityNotFoundException("L'artiste id "+id+" n'existe pas");
        }

        Artist artist = artistOptional.get();
        model.put("artist",artist);

        return "detailArtist";

    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                           liste artistes                                                           //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String listeArtists
            (
                    final ModelMap model ,
                    @RequestParam(defaultValue = "0")Integer page,
                    @RequestParam (defaultValue = "10")Integer size,
                    @RequestParam (defaultValue = "name")String sortProperty,
                    @RequestParam (defaultValue = "ASC")String sortDirection
            )
    {
//        System.out.println("liste de tous les artistes");
        if (page<0)
        {
            throw new IllegalArgumentException("la valeur de page ne peut pas etre négative");
        }


        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.fromString(sortDirection),sortProperty);

        Page<Artist> artistPage = artistRepository.findAll(pageRequest);

        if(page>=artistPage.getTotalPages()&&page!=0)
        {
            throw new EntityNotFoundException("la page demandée est supérieur aux nombre total de pages");
        }

        model.put("listeArtists",artistPage);


        return "listeArtists";
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                          liste artistes filtré par nom                                             //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET, value = "",params = {"name"})
    public String searchArtists
            (
                    final ModelMap model ,
                    @RequestParam String name,
                    @RequestParam (defaultValue = "0")Integer page,
                    @RequestParam (defaultValue = "10")Integer size,
                    @RequestParam (defaultValue = "name")String sortProperty,
                    @RequestParam (defaultValue = "ASC")String sortDirection
            )
    {
//        System.out.println("liste des artistes filtré par nom");
        if (page<0)
        {
            throw new IllegalArgumentException("la valeur de page ne peut pas etre négative");
        }

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.fromString(sortDirection),sortProperty);

        Page<Artist> artistPage = artistRepository.findArtistsByNameContains(name,pageRequest);

        if(page>=artistPage.getTotalPages()&&page!=0)
        {
            throw new EntityNotFoundException("la page demandée est supérieur aux nombre total de pages");
        }

        model.put("listeArtists",artistPage);

        if (!name.equals(""))
        {
            model.put("typeRecherche",name);
        }

        return "listeArtists";
    }





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    appel formulaire nouvel artist                                                  //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newArtist(final ModelMap model)
    {
//        System.out.println("on passe a la creation d'artist");
        Artist artist = new Artist();

        model.put("artist",artist);

        return "detailArtist";
    }





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    save artist                                                                     //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @RequestMapping
    (
        method = RequestMethod.POST,
        value = "",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String saveArtist(final ModelMap model, Artist artist)
    {
        System.out.println("on sauve l'artist ");
        System.out.println("nom de l'artist "+artist.getName());

        if(artist.getName().length()>120)
        {
            throw new MyException("le nom de l'artiste comporte trop de caractères");
        }

        if (artistRepository.existsByName(artist.getName()))
        {
            throw new NonUniqueResultException("l'artiste "+artist.getName()+" existe déja" );
        }
        if(artist.getName().equals(""))
        {
            throw  new MyException("le champ Nom ne doit pas être vide");
        }

        artistRepository.save(artist);

        model.put("artist",artistRepository.findByName(artist.getName()));

        return "detailArtist";
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    update artist                                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @RequestMapping
    (
        method = RequestMethod.PUT,
        value = "/{id}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String updateArtist(final ModelMap model, Artist artist, @PathVariable(value = "id") Long id)
    {
        System.out.println("on met a jour l'artist ");
        System.out.println("nom de l'artist "+artist.getName());

        if(artist.getName().length()>120)
        {
            throw new MyException("le nom de l'artiste comporte trop de caractères");
        }

        if (!artistRepository.existsById(id))
        {
            throw new EntityNotFoundException("Requète impossible : l'artiste d'id "+id+" n'éxiste pas");
        }

        if(artistRepository.existsByName(artist.getName()))
        {
            throw new NonUniqueResultException("un artiste avec ce nom existe déja !");
        }

        if(artist.getName().equals(""))
        {
            throw  new MyException("le champ Nom ne doit pas être vide");
        }

        Optional<Artist> artistOptional = artistRepository.findById(id);


        Artist updateArtist = artistOptional.get();
        updateArtist.setName(artist.getName());
        artistRepository.save(updateArtist);
        model.put("artist",updateArtist);

        return "detailArtist";
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    delete artist                                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public String deleteArtist(final ModelMap model, @PathVariable(value = "id") Long id)
    {
        System.out.println("delete celui la");

        if(!artistRepository.existsById(id))
        {
            throw new EntityNotFoundException("Suppresion impossible : l'artiste d'id "+id+" n'éxiste pas");
        }

        artistRepository.deleteById(id);

        
        return "accueil";
    }




}
