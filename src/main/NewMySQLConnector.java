package main;

import java.sql.*;

/**
 * Created by sndil on 4/25/2016.
 */
public class NewMySQLConnector {
    public static void main(String[] args) {
        String url = "custom://localhost:3306/new_schema";
        String username = "root";
        String password = "root";
        System.out.println("Connecting to database...");
        try  {
            Class.forName("main.CustomDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            resultSet = statement.executeQuery("select * from new_table");
            while (resultSet.next()){
                System.out.println(resultSet.getString("names"));
            }
        }
        catch (ClassNotFoundException e){
            throw new IllegalStateException("Class not found!", e);
        }
        catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
