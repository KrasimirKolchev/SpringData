package dbAppsIntroduction;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class demo {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username default (root): ");
        String user = scanner.nextLine().trim();
        user = user.equals("")? "root" : user;

        System.out.println("Enter password:");
        String password = scanner.nextLine();
        password = password.equals("")? "4321" : password;

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni?useSSL=false", props);

        PreparedStatement pstmt = connection.prepareStatement("Select * FROM employees WHERE salary > ?");
        System.out.println("Min salary (default 20000):");
        String salaryStr = scanner.nextLine();
        double salary = salaryStr.equals("")? 20000 : Double.parseDouble(salaryStr);

        pstmt.setDouble(1, salary);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            System.out.printf("|%-15.15s|%-15.15s|%10.2f|\n"
                    , resultSet.getString("first_name")
                    , resultSet.getString("last_name")
                    , resultSet.getDouble("salary"));
        }

        connection.close();
    }
}
