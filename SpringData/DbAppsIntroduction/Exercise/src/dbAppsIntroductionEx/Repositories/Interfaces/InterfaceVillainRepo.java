package dbAppsIntroductionEx.Repositories.Interfaces;

import dbAppsIntroductionEx.Entities.Minion;
import dbAppsIntroductionEx.Entities.Villain;

import java.util.List;

public interface InterfaceVillainRepo {
    Villain findById(int id);

    Villain findByName(String name);

    List<Villain> findAllVillains();

    List<Villain> findVillainsByMinionsGreaterThan(int value);

    void addMinionToVillain(Villain villain, Minion minion);

    void deleteAndReleaseMinions(int id);

    void save(Villain villain);
}
