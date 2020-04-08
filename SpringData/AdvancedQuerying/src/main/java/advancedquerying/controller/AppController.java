package advancedquerying.controller;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.domain.entities.Label;
import advancedquerying.domain.entities.Shampoo;
import advancedquerying.service.ingredientSvc.IngredientService;
import advancedquerying.service.labelSvc.LabelService;
import advancedquerying.service.shampooSvc.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppController implements CommandLineRunner {
    private final IngredientService ingredientService;
    private final LabelService labelService;
    private final ShampooService shampooService;

    @Autowired
    public AppController(IngredientService ingredientService, LabelService labelService, ShampooService shampooService) {
        this.ingredientService = ingredientService;
        this.labelService = labelService;
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //1.
        getShampoosBySize(reader);

        //2.
        //getShampoosByPriceOrLabelOrderByPriceAsc(reader);

        //3.
        //getShampoosByPriceOrderByPriceDesc(reader);

        //4.
        //getIngredientsByNameStartingWith(reader);

        //5.
        //getIngredientsByNameInOrderByPriceAsc(reader);

        //6.
        //getShampoosCountByPriceLowerThan(reader);

        //7.
        //selectShampoosByIngredientsIn(reader);

        //8.
        //getShampoosByIngredientsCountLessThan(reader);

        //9.
//        deleteIngredientByNameNamedQuery(reader);

        //10. Check prices manually
//        this.ingredientService.increaseAllIngredientsPriceBy10PercentsNamedQuery();

        //11. Check it manually
//        this.ingredientService
//                .increaseIngredientsPriceBy10PercentsForIngredientsNamesNamedQuery(
//                        Arrays.asList("Lavender", "Herbs", "Apple", "Invalid"));


    }

    private void deleteIngredientByNameNamedQuery(BufferedReader reader) throws IOException {
        System.out.println("Enter ingredient name to delete(check it manually): ");
        String name = reader.readLine().trim();

        this.ingredientService.deleteIngredientByName(name);
    }

    private void getShampoosByIngredientsCountLessThan(BufferedReader reader) throws IOException {
        System.out.println("Enter ingredients count:");
        int value = Integer.parseInt(reader.readLine());
        List<Shampoo> shampoos = this.shampooService.getShampoosByIngredientsCountLessThan(value);
        shampoos.forEach(s -> System.out.println(s.getBrand()));
    }

    private void selectShampoosByIngredientsIn(BufferedReader reader) throws IOException {
        System.out.println("Enter ingredients (separated by space(s)): ");
        String input = reader.readLine();

        List<Ingredient> data = Arrays.stream(input.split("\\s+"))
                .map(this.ingredientService::getByName)
                .collect(Collectors.toList());
        List<Shampoo> shampoos = this.shampooService.getShampoosByIngredientsIn(data);
        shampoos.forEach(s -> System.out.println(s.getBrand()));
    }

    private void getShampoosCountByPriceLowerThan(BufferedReader reader) throws IOException {
        System.out.println("Enter shampoo price:");
        BigDecimal price = new BigDecimal(reader.readLine());

        System.out.println("Count: " + this.shampooService.getShampoosCountByPriceLowerThan(price));
    }

    private void getIngredientsByNameInOrderByPriceAsc(BufferedReader reader) throws IOException {
        System.out.println("Enter ingredients (separated by space(s)): ");

        Set<String> data = Arrays.stream(reader.readLine().split("\\s+")).collect(Collectors.toSet());

        List<Ingredient> ingredients = this.ingredientService.getAllByNameInOrderByPriceAsc(data);
        ingredients.forEach(i -> System.out.println(i.getName()));
    }

    private void getIngredientsByNameStartingWith(BufferedReader reader) throws IOException {
        System.out.println("Enter ingredient name starting with:");
        String part = reader.readLine().trim();
        List<Ingredient> ingredients = this.ingredientService.getIngredientsByNameStartingWith(part);
        ingredients.forEach(i -> System.out.println(i.getName()));
    }

    private void getShampoosByPriceOrderByPriceDesc(BufferedReader reader) throws IOException {
        System.out.println("Enter shampoo price:");
        BigDecimal price = new BigDecimal(reader.readLine());
        List<Shampoo> shampoos = this.shampooService.getShampoosByPriceOrderByPriceDesc(price);
        shampoos.stream()
                .map(s -> String.format("%s %s %.2flv.", s.getBrand(), s.getSize(), s.getPrice()))
                .forEach(System.out::println);
    }

    private void getShampoosByPriceOrLabelOrderByPriceAsc(BufferedReader reader) throws IOException {
        System.out.println("Enter shampoo size:");
        String size = reader.readLine().trim();
        System.out.println("Enter shampoo label:");
        Label label = this.labelService.getLabelById(Integer.parseInt(reader.readLine()));

        List<Shampoo> shampoos = shampooService.getAllShampoosBySizeOrLabelOrderByPriceAsc(size, label);
        shampoos.stream()
                .map(s -> String.format("%s %s %.2flv.", s.getBrand(), s.getSize(), s.getPrice()))
                .forEach(System.out::println);
    }

    private void getShampoosBySize(BufferedReader reader) throws IOException {
        System.out.println("Enter shampoo size: ");
        String input = reader.readLine().trim();
        List<Shampoo> shampoos = shampooService.getAllShampoosBySizeOrderById(input);
        shampoos.stream()
                .map(s -> String.format("%s %s %.2flv.", s.getBrand(), s.getSize(), s.getPrice()))
                .forEach(System.out::println);
    }
}
