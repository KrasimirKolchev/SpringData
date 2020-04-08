package com.usersystem.services;

import com.usersystem.models.Picture;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PictureService {
    void registerPicture(Picture picture);

    void registerAllPictures(List<Picture> pictures);

    void deleteById(Long id);

    Picture getPictureById(Long id);

    long getAllPicturesSize();
}
