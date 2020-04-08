package services;

import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TownService {
    private EntityManager entityManager;

    public TownService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String removeTown(String tName) {
        Town town = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            tName = reader.readLine();

            town = this.entityManager
                    .createQuery("select t from Town as t where t.name = :tName", Town.class)
                    .setParameter("tName", tName)
                    .getResultList().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException ex) {
            return String.format("Entity Town(%s) doesn't exist in the DB!!!", tName);
        }

        List<Address> addresses = this.entityManager
                .createQuery("select a from Address as a where a.town = :town", Address.class)
                .setParameter("town", town)
                .getResultList();

        addresses.forEach(this.entityManager::remove);

        this.entityManager.remove(town);
        return String.format("%d addresses in %s deleted", addresses.size(), tName);
    }

    public void removeObjects() {
        List<Town> towns = this.entityManager.createQuery("select t from Town as t " +
                "where length(t.name) <= 5 ", Town.class).getResultList();
        this.entityManager.getTransaction().begin();
        towns.forEach(this.entityManager::detach);
        towns.forEach(t -> t.setName(t.getName().toLowerCase()));
        towns.forEach(this.entityManager::merge);

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

}
