package dbAppsIntroductionEx.Repositories.Interfaces;

import dbAppsIntroductionEx.Entities.Minion;
import dbAppsIntroductionEx.Repositories.RepositoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MinionRepository extends RepositoryImpl<Minion> implements InterfaceMinionRepo {
    private static final String TABLE_NAME = "minions";

    protected MinionRepository(Connection connection) {
        super(connection);
    }


    @Override
    public void saveMinion(Minion minion) {
        
    }

    @Override
    public List<Minion> findAllMinions() {
        return null;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Minion parseResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        int townId = resultSet.getInt("town_id");

        Minion minion = new Minion();
        minion.setId(id);
        minion.setName(name);
        minion.setAge(age);
        minion.setTownId(townId);

        return minion;
    }
}
