package com.iiitb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCconnectorUtil {
    private static final String connectionString = "jdbc:mysql://localhost:3306/oaes_schema";
    private static final String  user = "root";
    private static final String pass = "password";
    private static Connection conn;
    private static Statement stmt;

    static Connection getDBConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = DriverManager.getConnection(connectionString, user, pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    static Statement getStatement(Connection conn){
        try {
            stmt = conn.createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return stmt;
    }

    static void closeConnection(){
        try {
            conn.close();
            stmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
