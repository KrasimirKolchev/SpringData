package com.usersystem.services;

import com.usersystem.models.Album;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumService {
    void registerAlbum(Album album);

    void registerAllAlbums(List<Album> albums);

    void deletById(Long id);

    Album getAlbumById(Long id);
}
