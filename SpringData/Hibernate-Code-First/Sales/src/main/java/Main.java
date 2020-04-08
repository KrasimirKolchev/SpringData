import entity.Customer;
import entity.Product;
import entity.Sale;
import entity.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Customer customer = new Customer();
        customer.setEmail("ivan.ivanov@abv.bg");
        customer.setName("Ivan Ivanov");
        customer.setCreditCardNumber("123456789QWERTY");

        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setLocationName("Store Location");

        Product product = new Product();
        product.setName("Product");
        product.setPrice(BigDecimal.valueOf(11.99));
        product.setQuantity(1);

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setProduct(product);
        sale.setStoreLocation(storeLocation);
        sale.setDate(new Date());


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("salesDB");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();

            manager.persist(customer);
            manager.persist(product);
            manager.persist(storeLocation);
            manager.persist(sale);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }

        manager.close();
    }
}
