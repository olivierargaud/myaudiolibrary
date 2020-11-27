package com.myaudiolibrary.web;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MyRunner implements CommandLineRunner
{

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;


    @Override
    public void run(String... args) throws Exception
    {


        System.out.println("salut web");
        System.out.println("nombre d'artist :" +artistRepository.count());
        System.out.println("nombre d'album :" + albumRepository.count());

        albumRepository.findAll();
        Artist queen = artistRepository.findByName("Queen");
        System.out.println(queen.getName()+" "+queen.getId());
        System.out.println(artistRepository.findByName("Queen").toString());
        artistRepository.findByName("Queen").getAlbums();
//        ArrayList<Artist> listArtist = (ArrayList<Artist>) artistRepository.findAll();
//        for ( Artist artist: listArtist )
//        {
//            System.out.println(artist);
//        }
        Set<Album> listAlbum = queen.getAlbums();
        System.out.println(listAlbum.size());
        for (Album album :  listAlbum)
        {
            System.out.println(album.toString());
        }

    }
}
