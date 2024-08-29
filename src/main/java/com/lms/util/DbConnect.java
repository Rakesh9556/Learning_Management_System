package com.lms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
	// 1. create a method to establish and return database connection
	public Connection getConnnection() throws SQLException, ClassNotFoundException {
		
		// 2. Define database fields
		String DB_URI = "";
		String DB_USER = "";
		String DB_PASSWORD = "";
		
		Connection conn;
		
		try {
			// 3. Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 4. Create a connection object
			conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("MySQL JDBC Driver not found");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Unable to connect to the database");
		}
		
		// 5. Return the connection
		return conn;
	}

}
