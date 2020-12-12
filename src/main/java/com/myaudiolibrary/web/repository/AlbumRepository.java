package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>
{
    Album findByTitle(String title);

    Album findAlbumByTitleAndArtist_Id(String title, Long artistId);

    boolean existsAlbumByTitleAndArtist_Id(String title, Long artistId);

}
