package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArtistRepository extends JpaRepository<Artist, Long>
{
    Artist findByName(String nom);

    @Query(value = "SELECT * FROM Artist WHERE name LIKE %:nom%", nativeQuery = true)
    Page<Artist> findArtistLike(@Param("nom")String nom, Pageable pageable);

    Page<Artist> findArtistsByNameContains(String nom, Pageable pageable);
}
