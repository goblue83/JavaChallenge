package com.tw.javachallenge;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
	protected static Connection initializeDatabase()
	        throws SQLException, ClassNotFoundException {
	        // Initialize all the information regarding
	        // Database Connection
	        String dbDriver = "com.mysql.jdbc.Driver";
	        String dbURL = "jdbc:mysql://sql5.freesqldatabase.com/";
	        // Database name to access
	        String dbName = "sql5456993";
	        String dbUsername = "sql5456993";
	        String dbPassword = "se5nNUJZmh";
	  
	        Class.forName(dbDriver);
	        Connection con = DriverManager.getConnection(dbURL + dbName,
	                                                     dbUsername, 
	                                                     dbPassword);
	        return con;
	    
	}
}
