package advancedquerying.repository;

import advancedquerying.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> getAllByNameStartingWith(String pattern);

    List<Ingredient> getAllByNameInOrderByPriceAsc(Set<String> ingredients);

    Ingredient getFirstByName(String name);

    @Modifying
    @Transactional
    void deleteIngredientByName(@Param("name") String name);

    @Modifying
    @Transactional
    void increaseAllIngredientsPriceBy10PercentsNamedQuery();

    @Modifying
    @Transactional
    void increaseIngredientsPriceBy10PercentsForIngredientsNamesNamedQuery(@Param("names") List<String> names);
}
