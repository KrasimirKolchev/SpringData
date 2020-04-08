package advancedquerying.service.shampooSvc;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.domain.entities.Label;
import advancedquerying.domain.entities.Shampoo;
import advancedquerying.domain.entities.Size;
import advancedquerying.repository.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    private ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> getAllShampoosBySizeOrderById(String size) {
        Size value = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.getAllBySize(value);
    }

    @Override
    public List<Shampoo> getAllShampoosBySizeOrLabelOrderByPriceAsc(String size, Label label) {
        Size value = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.getAllBySizeOrLabelOrderByPriceAsc(value, label);
    }

    @Override
    public List<Shampoo> getShampoosByPriceOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.getShampoosByPriceAfterOrderByPriceDesc(price);
    }

    @Override
    public Long getShampoosCountByPriceLowerThan(BigDecimal price) {
        return this.shampooRepository.countShampooByPriceBefore(price);
    }

    @Override
    public List<Shampoo> getShampoosByIngredientsIn(List<Ingredient> ingredients) {
        return this.shampooRepository.selectShampoosByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> getShampoosByIngredientsCountLessThan(int value) {
        return this.shampooRepository.selectShampoosByIngredientsCountLessThan(value);
    }
}
