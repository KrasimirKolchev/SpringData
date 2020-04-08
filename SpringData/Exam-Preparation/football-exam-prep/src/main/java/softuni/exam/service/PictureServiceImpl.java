package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureRootDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper modelMapper,
                              ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        PictureRootDTO pictures = this.xmlParser
                .importFromXML(PictureRootDTO.class, "src/main/resources/files/xml/pictures.xml");

        StringBuilder sb = new StringBuilder();

        pictures.getPictures()
                .forEach(p -> {
                    if (this.validatorUtil.isValid(p)) {
                        this.pictureRepository.saveAndFlush(this.modelMapper.map(p, Picture.class));
                        sb.append(String.format("Successfully imported picture - %s", p.getUrl()));
                    } else {
                        sb.append("Invalid picture");
                    }
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/pictures.xml"));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.getPictureByUrl(url);
    }
}
