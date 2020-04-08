package dbAppsIntroductionEx.Repositories.Interfaces;

import dbAppsIntroductionEx.Entities.Minion;

import java.util.List;

public interface InterfaceMinionRepo {
    Minion findById(int id);

    Minion findByName(String name);

    boolean deleteById(int id);

    void saveMinion(Minion minion);

    List<Minion> findAllMinions();
}
