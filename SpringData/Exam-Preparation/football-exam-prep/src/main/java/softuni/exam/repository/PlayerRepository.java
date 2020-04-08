package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.domain.entities.Player;

import java.math.BigDecimal;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByTeamName(String team);

    List<Player> findPlayersBySalaryAfterOrderBySalaryDesc(BigDecimal salary);

}
