package dbAppsIntroductionEx.Repositories.Interfaces;

import dbAppsIntroductionEx.Entities.Town;

import java.util.List;

public interface InterfaceTownRepo {
    Town findById(int id);

    Town findByName(String name);

    boolean deleteById(int id);

    List<Town> findAllTowns();

    List<Town> findTownsByCountry(String name);

    void save(Town town);
}
