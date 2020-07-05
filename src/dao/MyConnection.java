package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	public static String driverName = "com.mysql.cj.jdbc.Driver";
	public static String uri = "jdbc:mysql://localhost:3306/goback?user=root&password=12345&useSSL=true&serverTimezone=UTC";

	
	public static String getDriverName() {
		return driverName;
	}


	public static String getUri() {
		return uri;
	}

	public static Connection getConnection(String driverName, String uri) {
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(uri);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return conn;
		}
	}

	public static Connection getConnection(String driverName, String uri, String user, String password) {
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(uri, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return conn;
		}
	}
}