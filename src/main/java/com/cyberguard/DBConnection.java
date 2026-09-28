package com.cyberguard;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String host = System.getenv("mysql.railway.internal");
            String port = System.getenv("3306");
            String database = System.getenv("railway");
            String user = System.getenv("root");
            String password = System.getenv("JYKzMQTjRYvUuMSiuIFavuUDHHpOFqil");

            String url =
                    "jdbc:mysql://mysql.railway.internal:3306/railway";

            Connection con =
                    DriverManager.getConnection(
                            url,
                            user,
                            password);

            System.out.println("MySQL Connected Successfully!");

            return con;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}