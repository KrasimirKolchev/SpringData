package advancedquerying.service.ingredientSvc;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getIngredientsByNameStartingWith(String pattern) {
        return this.ingredientRepository.getAllByNameStartingWith(pattern);
    }

    @Override
    public List<Ingredient> getAllByNameInOrderByPriceAsc(Set<String> ingredients) {
        return this.ingredientRepository.getAllByNameInOrderByPriceAsc(ingredients);
    }

    @Override
    public Ingredient getByName(String name) {
        return this.ingredientRepository.getFirstByName(name);
    }

    @Override
    public void deleteIngredientByName(String name) {
        this.ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    public void increaseAllIngredientsPriceBy10PercentsNamedQuery() {
        this.ingredientRepository.increaseAllIngredientsPriceBy10PercentsNamedQuery();
    }

    @Override
    public void increaseIngredientsPriceBy10PercentsForIngredientsNamesNamedQuery(List<String> names) {
        this.ingredientRepository.increaseIngredientsPriceBy10PercentsForIngredientsNamesNamedQuery(names);
    }
}
