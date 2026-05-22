package com.management;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnectionManager {

    
//    private static final String DB_URL      = "jdbc:mysql://localhost:3306/online_polling_system";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "123456789"; 

    
    private static Connection connection = null;

    
    public static Connection getConnection() throws SQLException {
        try{

        	    Properties prop=new Properties();

        	    
        	    FileInputStream fs=new FileInputStream("src/Database.properties");

                
               if(connection == null || connection.isClosed()) {
            	   prop.load(fs);
//                   Class.forName("com.mysql.cj.jdbc.Driver");
                   
                   connection=DriverManager.getConnection(prop.getProperty("DB_URL"),prop.getProperty("DB_USERNAME"),prop.getProperty("DB_PASSWORD"));
                   		

//                   connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                   System.out.println("Connection established successfully.");
               }
               
           
        } catch (Exception e) {
               System.out.println(e.getMessage());
        }
        return connection;
    }

   
    public static void closeConnection() {
         try {     
             connection.close();
             System.out.println("Connection closed successfully.");
            } catch (Exception e) {
                System.err.println( e.getMessage());
            }
    }

  
    
}
