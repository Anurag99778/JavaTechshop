package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getMyDBConnection() {
      try {  
            String url = "jdbc:mysql://localhost:3306/tech_shop2";
            String un = "root";
            String pass = "828398";
            
            System.out.println(url + " " + un + " " + pass);

            connection = DriverManager.getConnection(url, un, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return connection;
    } 

    public static void main(String[] args) {
        System.out.println( "Connected to db:::" + getMyDBConnection());
    }
}
