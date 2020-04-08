package advancedquerying.repository;

import advancedquerying.domain.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Label, Long> {

}
