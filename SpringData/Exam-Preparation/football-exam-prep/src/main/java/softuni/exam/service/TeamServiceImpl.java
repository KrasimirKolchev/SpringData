package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PictureService pictureService;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ValidatorUtil validatorUtil,
                           ModelMapper modelMapper, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        TeamRootDto teams = this.xmlParser
                .importFromXML(TeamRootDto.class, "src/main/resources/files/xml/teams.xml");

        StringBuilder sb = new StringBuilder();

        teams.getTeams().stream()
                .map(t -> {
                    t.setPicture(this.pictureService.getPictureByUrl(t.getPicture().getUrl()));
                    return t;
                })
                .forEach(t -> {
                    if (this.validatorUtil.isValid(t)) {
                        sb.append(String.format("Successfully imported team - %s", t.getName()));
                        this.teamRepository.saveAndFlush(this.modelMapper.map(t, Team.class));
                    } else {
                        sb.append("Invalid team");
                    }
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/teams.xml"));
    }

    @Override
    public Team getTeamByName(String name) {
        return this.teamRepository.getTeamByName(name);
    }
}
