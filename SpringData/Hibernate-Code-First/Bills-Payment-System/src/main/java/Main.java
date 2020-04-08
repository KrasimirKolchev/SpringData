import entities.BankAccount;
import entities.BillingDetails;
import entities.CreditCard;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        CreditCard card1 = new CreditCard();
        card1.setNumber("123456789");
        card1.setCardType("American Express");
        card1.setExpirationMonth(12);
        card1.setExpirationYear(2023);

        CreditCard card2 = new CreditCard();
        card2.setNumber("987654321");
        card2.setCardType("MasterCard");
        card2.setExpirationMonth(3);
        card2.setExpirationYear(2022);

        BankAccount bank1 = new BankAccount();
        bank1.setNumber("ASD123456789");
        bank1.setBankName("aknfaslknf");
        bank1.setSwiftCode("BGSF");

        BankAccount bank2 = new BankAccount();
        bank2.setNumber("DSA987654321");
        bank2.setBankName("anfasljnfsxcsx,cva;ls");
        bank2.setSwiftCode("JKBFAASD");

        User user1 = new User();
        user1.setFirstName("Ivan");
        user1.setLastName("Ivanov");
        user1.setEmail("ivan_ivanov@abv.bg");
        user1.setPassword("456321");
        user1.setBillingDetails(new HashSet<>());

        User user2 = new User();
        user2.setFirstName("Ivan");
        user2.setLastName("Ivanov");
        user2.setEmail("ivan_ivanov@abv.bg");
        user2.setPassword("456321");
        user2.setBillingDetails(new HashSet<>());

        card1.setOwner(user2);
        card2.setOwner(user1);

        bank1.setOwner(user1);
        bank1.setOwner(user2);

        user1.getBillingDetails().add(card2);
        user1.getBillingDetails().add(bank1);

        user2.getBillingDetails().add(card1);
        user2.getBillingDetails().add(bank2);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bills_payment_system");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.persist(card1);
            manager.persist(card2);

            manager.persist(bank1);
            manager.persist(bank2);

            manager.persist(user1);
            manager.persist(user2);

            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
