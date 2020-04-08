package advancedquerying.service.shampooSvc;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.domain.entities.Label;
import advancedquerying.domain.entities.Shampoo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ShampooService {

    List<Shampoo> getAllShampoosBySizeOrderById(String size);

    List<Shampoo> getAllShampoosBySizeOrLabelOrderByPriceAsc(String size, Label label);

    List<Shampoo> getShampoosByPriceOrderByPriceDesc(BigDecimal price);

    Long getShampoosCountByPriceLowerThan(BigDecimal price);

    List<Shampoo> getShampoosByIngredientsIn(List<Ingredient> ingredients);

    List<Shampoo> getShampoosByIngredientsCountLessThan(int value);


}
