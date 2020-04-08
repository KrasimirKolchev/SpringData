package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerSeedDTO;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;


import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PictureService pictureService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, PictureService pictureService, TeamService teamService,
                             ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.pictureService = pictureService;
        this.teamService = teamService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        PlayerSeedDTO[] players = this.gson
                .fromJson(new FileReader("src/main/resources/files/json/players.json")
                        , PlayerSeedDTO[].class);

        StringBuilder sb = new StringBuilder();

        Arrays.stream(players).forEach(p -> {

            if (this.validatorUtil.isValid(p)) {
                Player player = this.modelMapper.map(p, Player.class);
                player.setTeam(this.teamService.getTeamByName(p.getTeam().getName()));
                player.setPicture(this.pictureService.getPictureByUrl(p.getPicture().getUrl()));

                sb.append(String.format("Successfully imported player: %s %s"
                        , player.getFirstName(), player.getLastName()));
                this.playerRepository.saveAndFlush(player);
            } else {
                sb.append("Invalid player");
            }
            sb.append(System.lineSeparator());
        });

       return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/players.json"));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        List<Player> players = this.playerRepository
                .findPlayersBySalaryAfterOrderBySalaryDesc(new BigDecimal(100000));

        StringBuilder sb = new StringBuilder();

        players.forEach(p ->
                sb.append(String.format("\nPlayer name: %s %s\n\tNumber: %d\n\tSalary: %s\n\tTeam: %s",
                        p.getFirstName(), p.getLastName(), p.getNumber(), p.getSalary(), p.getTeam().getName())));

       return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        List<Player> players = this.playerRepository.findAllByTeamName("North Hub");

        StringBuilder sb = new StringBuilder();
        sb.append("Team: North Hub");
        players.forEach(p -> sb.append("\n\t").append(String.format("Player name: %s %s - %s\n\tNumber: %d",
                p.getFirstName(), p.getLastName(), p.getPosition(), p.getNumber())));


        return sb.toString();
    }


}
