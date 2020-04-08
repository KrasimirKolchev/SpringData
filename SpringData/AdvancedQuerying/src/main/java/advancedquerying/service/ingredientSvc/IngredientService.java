package advancedquerying.service.ingredientSvc;

import advancedquerying.domain.entities.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IngredientService {

    List<Ingredient> getIngredientsByNameStartingWith(String pattern);

    List<Ingredient> getAllByNameInOrderByPriceAsc(Set<String> ingredients);

    Ingredient getByName(String name);

    void deleteIngredientByName(String name);

    void increaseAllIngredientsPriceBy10PercentsNamedQuery();

    void increaseIngredientsPriceBy10PercentsForIngredientsNamesNamedQuery(List<String> names);
}
