package com.usersystem.repositories;

import com.usersystem.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album getAlbumById(Long id);
}
