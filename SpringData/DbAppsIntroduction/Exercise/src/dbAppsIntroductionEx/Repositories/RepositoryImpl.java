package dbAppsIntroductionEx.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryImpl<T> {
    private Connection connection;

    protected RepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        return this.connection;
    }

    protected abstract String getTableName();
    protected abstract T parseResult(ResultSet resultSet) throws SQLException;

    public T findById(int id) {
        String query = String.format("SELECT * FROM %s WHERE id = %d", this.getTableName(), id);

        T res = null;

        try {
            PreparedStatement pstms = this.connection.prepareStatement(query);
            ResultSet resultSet = pstms.executeQuery();

            if (resultSet.next()) {
                res = this.parseResult(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public T findByName(String name) {
        String query = String.format("SELECT * FROM %s WHERE `name` = %s", this.getTableName(), name);

        T res = null;

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                res = this.parseResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public List<T> findAll() {
        String query = String.format("SELECT * FROM %s", this.getTableName());
        List<T> res = new ArrayList<>();

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                res.add(parseResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public boolean deleteById(int id) {
        String query = String.format("DELETE FROM %s WHERE id = %d", this.getTableName(), id);

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
