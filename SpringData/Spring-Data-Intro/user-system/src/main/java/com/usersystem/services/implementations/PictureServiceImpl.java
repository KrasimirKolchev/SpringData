package com.usersystem.services.implementations;

import com.usersystem.models.Picture;
import com.usersystem.repositories.PictureRepository;
import com.usersystem.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void registerPicture(Picture picture) {
        this.pictureRepository.save(picture);
    }

    @Override
    public void registerAllPictures(List<Picture> pictures) {
        this.pictureRepository.saveAll(pictures);
    }

    @Override
    public void deleteById(Long id) {
        this.pictureRepository.deleteById(id);
    }

    @Override
    public Picture getPictureById(Long id) {
        return this.pictureRepository.getPictureById(id);
    }

    @Override
    public long getAllPicturesSize() {
        return this.pictureRepository.count();
    }
}
