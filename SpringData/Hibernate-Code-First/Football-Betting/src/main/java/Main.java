import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Bet bet = new Bet();
        bet.setBetMoney(BigDecimal.valueOf(10));
        bet.setDateAndTimeOfBet(new Date());

        BetGame betGame = new BetGame();

        Color color = new Color();
        color.setName("Green");
        Color color1 = new Color();
        color1.setName("Blue");

        Competition competition = new Competition();
        competition.setName("comp");

        CompetitionType competitionType = new CompetitionType();
        competitionType.setName("International");

        Continent continent = new Continent();
        continent.setName("Europe");
        continent.setCountries(new HashSet<>());

        Country country = new Country();
        country.setContinent(new HashSet<>());
        country.getContinent().add(continent);
        country.setId("GE");
        country.setName("Germany");

        continent.getCountries().add(country);

        Game game = new Game();
        game.setAwayGoals(1);
        game.setAwayTeamWinBetRate(1.22);
        game.setCompetition(competition);
        game.setDateAndTimeOfGame(new Date());
        game.setDrawGameBetRate(2.8);
        game.setHomeGoals(2);
        game.setHomeTeamWinBetRate(2.3);

        Player player = new Player();
        player.setCurrentlyInjured(false);
        player.setName("player name");
        player.setSquadNumber(22);

        PlayerStatistics playerStatistics = new PlayerStatistics();
        playerStatistics.setGame(game);
        playerStatistics.setPlayedMinutesDuringGame(90);
        playerStatistics.setPlayer(player);
        playerStatistics.setPlayerAssists(3);
        playerStatistics.setScoredGoals(2);

        Position position = new Position();
        position.setId("GK");
        position.setPositionDescription("position description");

        ResultPrediction resultPrediction = new ResultPrediction();
        resultPrediction.setPrediction("win");

        Round round = new Round();
        round.setName("league");

        Team team = new Team();
        team.setBudget(BigDecimal.valueOf(10000));
        team.setInitials("LIV");
        team.setName("team 1");
        team.setPrimaryKitColor(color);
        team.setSecondaryKitColor(color1);

        Team team1 = new Team();
        team1.setBudget(BigDecimal.valueOf(1022000));
        team1.setInitials("ASD");
        team1.setName("team 2");
        team1.setPrimaryKitColor(color1);
        team1.setSecondaryKitColor(color);

        Town town = new Town();
        town.setName("Town");
        town.setCountry(country);

        User user = new User();
        user.setPassword("1230");
        user.setUsername("username");
        user.setBalance(BigDecimal.valueOf(123456));

        betGame.setGame(game);
        betGame.setBet(bet);
        team.setTown(town);
        team1.setTown(town);
        player.setTeam(team);
        player.setPosition(position);
        game.setRound(round);
        game.setHomeTeam(team);
        game.setAwayTeam(team1);
        competition.setType(competitionType);
        bet.setUser(user);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("football_betting_db");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.persist(bet);

            manager.persist(color);
            manager.persist(color1);
            manager.persist(competition);
            manager.persist(competitionType);
            manager.persist(continent);
            manager.persist(country);
            manager.persist(game);
            manager.persist(player);
            manager.persist(playerStatistics);
            manager.persist(position);
            manager.persist(resultPrediction);
            manager.persist(betGame);

            manager.persist(round);
            manager.persist(team);
            manager.persist(team1);
            manager.persist(town);
            manager.persist(user);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
