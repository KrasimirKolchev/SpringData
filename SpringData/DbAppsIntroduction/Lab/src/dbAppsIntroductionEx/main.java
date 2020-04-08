package dbAppsIntroductionEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class main {
    private static final String USER = "root";
    private static final String PASSWORD = "4321";

    public static void Main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter username default (root): ");
        String username = reader.readLine().trim();
        username = username.equals("") ? USER : username;

        System.out.println("Enter password default (4321): ");
        String psw = reader.readLine().trim();
        psw = psw.equals("") ? PASSWORD : psw;

        Properties prop = new Properties();
        prop.setProperty("user", username);
        prop.setProperty("password", psw);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?useSSL=false", prop);


        //2
         getVillainsNames(connection, reader);

        //3
         getMinionNames(connection, reader);

        //4
         addMinion(connection, reader);

        //5
         changeCountryNamesCasting(connection, reader);

        //6
         removeVillain(connection, reader);

        //7
         printAllMinionNames(connection);

        //8
         increaseMinionsAge(connection, reader);

        //9
         increaseAgeStoredProcedure(connection, reader);

    }

    private static void increaseAgeStoredProcedure(Connection connection, BufferedReader reader) throws IOException, SQLException {
        System.out.println("Enter minion id default(1):");
        String input = reader.readLine().trim();
        input = input.equals("") ? "1" : input;

        if (!isNumeric(input)) {
            System.out.println("Invalid input type for id!!!");
            return;
        }

        int minionId = Integer.parseInt(input);
        String query = String.format("CALL usp_get_older(%d);", minionId);
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.execute();

        query = String.format("SELECT `name`, age FROM minions WHERE id = %d", minionId);
        pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery(query);

        if (resultSet.next()) {
            System.out.printf("%s %d", resultSet.getString("name"), resultSet.getInt("age"));
        } else {
            System.out.println("Invalid id!!");
        }
    }

    private static void increaseMinionsAge(Connection connection, BufferedReader reader) throws IOException, SQLException {
        System.out.println("Enter minions id default(2 1 4):");

        String data = reader.readLine();
        data = data.trim().equals("") ? "2 1 4" : data;

        String[] input = data.split("\\s+");
        List<Integer> minionIds = new ArrayList<>();

        for (String id : input) {
            if (!isNumeric(id)) {
                minionIds = List.of(2, 1, 4);
                break;
            }
            minionIds.add(Integer.parseInt(id));
        }

        for (int id : minionIds) {
            String query = String.format("UPDATE minions SET age = age + 1, `name` = LCASE(`name`)WHERE id = %d;", id);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
        }

        printAllMinionsNamesAndAge(connection);
    }

    private static void printAllMinionsNamesAndAge(Connection connection) throws SQLException {
        String query = "SELECT `name`, age FROM minions";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }
    }

    private static void printAllMinionNames(Connection connection) throws SQLException {
        System.out.println("Printing all minion names in the database (first record, last record)!!");

        String query = "SELECT `name` FROM minions";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();

        List<String> names = new ArrayList<>();

        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }

        for (int i = 0; i < names.size() / 2; i++) {
            System.out.printf("%s%n%s%n", names.get(i), names.get(names.size() - 1 - i));
        }
    }

    private static void removeVillain(Connection connection, BufferedReader reader) throws IOException, SQLException {
        System.out.println("Enter villain id default(1): ");
        String str = reader.readLine();

        str = str.trim().equals("") ? "1" : str;

        int vId;
        if (isNumeric(str)) {
            vId = Integer.parseInt(str);
        } else {
            System.out.println("Invalid input type for id!!!");
            return;
        }

        String query = String.format("SELECT * FROM villains WHERE id = '%d'", vId);
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();

        if (resultSet.next()) {
            System.out.printf("%s was deleted%n", resultSet.getString("name"));
            System.out.println(releaseMinions(vId, connection));
        } else {
            System.out.println("No such villain was found");
        }
    }

    private static String releaseMinions(int vId, Connection connection) throws SQLException {
        String query = String.format("DELETE minions_villains FROM minions_villains WHERE villain_id = %d", vId);
        Statement stmt = connection.createStatement();
        int count = stmt.executeUpdate(query);

        return String.format("%d minions released", count);
    }

    public static void changeCountryNamesCasting(Connection connection, BufferedReader reader) throws IOException, SQLException {
        System.out.println("Enter country default (Bulgaria)");
        String cName = reader.readLine().trim();
        cName = cName.equals("") ? "Bulgaria" : cName;

        String query = String.format("SELECT * FROM towns WHERE country = '%s'", cName);
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No town names were affected.");
        } else {
            List<String> outputMsg = new ArrayList<>();
            resultSet.previous();
            int count = 0;
            while (resultSet.next()) {
                String tName = resultSet.getString("name");
                outputMsg.add(tName.toUpperCase());
                query = String.format("UPDATE towns SET `name` = '%s' WHERE `name` = '%s'"
                        , tName.toUpperCase(), tName);
                pstmt = connection.prepareStatement(query);
                pstmt.executeUpdate();
                count++;
            }

            System.out.printf("%d town names were affected.%n", count);
            System.out.printf("[%s]", String.join(", ", outputMsg));
        }

    }

    public static void getVillainsNames(Connection connection, BufferedReader reader) throws SQLException, IOException {
        System.out.println("Enter min minions count default (15): ");

        PreparedStatement pstmt = connection.prepareStatement(
                "SELECT v.name, COUNT(mv.minion_id) AS `count` FROM villains as v " +
                        "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                        "GROUP BY v.name HAVING count > ? " +
                        "ORDER BY count DESC");

        String input = reader.readLine().trim();
        input = input.equals("") ? "15" : input;
        int minMinionsCount = Integer.parseInt(input);

        pstmt.setInt(1, minMinionsCount);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n"
                    , resultSet.getString("name")
                    , resultSet.getInt("count"));
        }
    }

    public static void addMinion(Connection connection, BufferedReader reader) throws SQLException, IOException {

        System.out.println("Enter minion data default(Carry 20 Eindhoven): ");
        List<String> minionData = Arrays.stream(reader.readLine().replace("Minion:", "")
                .split("\\s+")).collect(Collectors.toList());
        minionData = minionData.size() != 3 ? Arrays.asList("Carry", "20", "Eindhoven") : minionData;

        System.out.println("Enter Villain name: default(Jimmy)");
        List<String> villainData = Arrays.stream(reader.readLine().replace("Villain:", "")
                .split("\\s+")).collect(Collectors.toList());
        String villainName = villainData.get(0);
        villainName = villainName.equals("") ? "Jimmy" : villainName;

        String query;

        query = String.format("SELECT * FROM towns WHERE `name` = '%s'", minionData.get(2));
        PreparedStatement pstmt = connection.prepareStatement(query);

        ResultSet resultSet = pstmt.executeQuery();

        int townId = 0;
        if (!resultSet.next()) {
            query = String.format("INSERT INTO towns (`name`) VALUES ('%s')", minionData.get(2));
            pstmt = connection.prepareStatement(query, RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();

            if (resultSet.next()) {
                townId = resultSet.getInt(1);
            }

            System.out.printf("Town %s was added to the database.%n", minionData.get(2));
        } else {
            townId = resultSet.getInt("id");
        }

        query = String.format("INSERT INTO minions (`name`, age, town_id) VALUES ('%s', %d, %d)"
                , minionData.get(0), Integer.parseInt(minionData.get(1)), townId);
        pstmt = connection.prepareStatement(query, RETURN_GENERATED_KEYS);
        pstmt.executeUpdate();
        resultSet = pstmt.getGeneratedKeys();

        int minionId = 0;
        if (resultSet.next()) {
            minionId = resultSet.getInt(1);
        }

        query = String.format("SELECT * FROM villains WHERE `name` = '%s'", villainName);
        pstmt = connection.prepareStatement(query);
        resultSet = pstmt.executeQuery();

        String DEF_EVILNESS = "evil";

        int villainId2 = 0;
        if (!resultSet.next()) {
            query = String.format("INSERT INTO villains (`name`, evilness_factor) VALUES ('%s', '%s')", villainName, DEF_EVILNESS);
            pstmt = connection.prepareStatement(query, RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();

            if (resultSet.next()) {
                villainId2 = resultSet.getInt(1);
            }

            System.out.printf("Villain %s was added to the database.%n", villainName);
        } else {
            villainId2 = resultSet.getInt("id");
        }

        query = String.format("INSERT INTO minions_villains (minion_id, villain_id) VALUES (%d, %d)"
                , minionId, villainId2);
        pstmt = connection.prepareStatement(query);
        pstmt.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s.%n", minionData.get(0), villainName);
    }

    public static void getMinionNames(Connection connection, BufferedReader reader) throws SQLException, IOException {

        System.out.println("Enter villain id default(1): ");
        String id = reader.readLine().trim();
        id = id.equals("") ? "1" : id;
        int villainId = Integer.parseInt(id);

        String query = String.format("SELECT v.`name` AS villain_name, m.`name`, m.age FROM villains AS v\n" +
                "JOIN minions_villains AS mv ON v.id = mv.villain_id\n" +
                "JOIN minions as m ON m.id = mv.minion_id\n" +
                "WHERE v.id = %d", villainId);
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();

        if (resultSet.next()) {
            System.out.printf("Villain: %s%n", resultSet.getString("villain_name"));

            int count = 1;
            resultSet.previous();

            while (resultSet.next()) {
                System.out.printf("%d. %s %d%n", count++
                        , resultSet.getString("name")
                        , resultSet.getInt("age"));
            }
        } else {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
        }
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}