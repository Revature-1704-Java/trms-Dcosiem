package com.revature.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleDriver;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException, IOException {
		Properties prop = new Properties();
		InputStream in = new FileInputStream("D:\\Vit\\TuitionReimbursementSystem\\connection.properties");
//		InputStream in = new FileInputStream("connection.properties");
		prop.load(in);
		
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		
		DriverManager.registerDriver(new OracleDriver());
		
		return DriverManager.getConnection(url, user, password);
	}
}
