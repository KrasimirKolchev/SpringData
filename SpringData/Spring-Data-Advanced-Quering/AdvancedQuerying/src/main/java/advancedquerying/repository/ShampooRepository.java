package advancedquerying.repository;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.domain.entities.Label;
import advancedquerying.domain.entities.Shampoo;
import advancedquerying.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> getAllBySize(Size size);

    List<Shampoo> getAllBySizeOrLabelOrderByPriceAsc(Size size, Label label);

    List<Shampoo> getShampoosByPriceAfterOrderByPriceDesc(BigDecimal price);

    Long countShampooByPriceBefore(BigDecimal price);


    @Query(value = "select s from advancedquerying.domain.entities.Shampoo as s " +
            "join s.ingredients as i where i in :ingredients")
    List<Shampoo> selectShampoosByIngredients(@Param("ingredients") List<Ingredient> ingredients);

    @Query("select s from advancedquerying.domain.entities.Shampoo as s where size(s.ingredients) < :value")
    List<Shampoo> selectShampoosByIngredientsCountLessThan(@Param("value") int value);


}
