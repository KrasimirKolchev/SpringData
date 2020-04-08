package com.usersystem.services.implementations;

import com.usersystem.models.Album;
import com.usersystem.repositories.AlbumRepository;
import com.usersystem.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public void registerAlbum(Album album) {
        this.albumRepository.save(album);
    }

    @Override
    public void registerAllAlbums(List<Album> albums) {
        this.albumRepository.saveAll(albums);
    }

    @Override
    public void deletById(Long id) {
        this.albumRepository.deleteById(id);
    }

    @Override
    public Album getAlbumById(Long id) {
        return this.albumRepository.getAlbumById(id);
    }

}
