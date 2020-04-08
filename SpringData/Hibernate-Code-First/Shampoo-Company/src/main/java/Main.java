import model.ingredients.implementations.*;
import model.labels.implementations.BasicLabel;
import model.shampoos.implementations.BasicShampoo;
import model.shampoos.implementations.FreshNuke;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("shampoo_company");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();
        BasicIngredient lavender = new Lavender();

        BasicLabel label = new BasicLabel("Fresh Nuke Shampoo", "Contains mint and nettle");
        BasicShampoo shampoo = new FreshNuke(label);
        shampoo.getIngredients().add(mint);
        shampoo.getIngredients().add(nettle);
        shampoo.getIngredients().add(am);
        entityManager.persist(shampoo);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
