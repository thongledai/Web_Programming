package vn.iotstar.configs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private final String serverName = "localhost";
	private final String dbName = "WebProgramming";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "sa";
	private final String password = "123";

	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=WebProgramming;" + "encrypt=true;"
				+ "trustServerCertificate=true";

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		return DriverManager.getConnection(url, userID, password);
	}

	public static void main(String[] args) {
		try {
			Connection conn = new DBConnection().getConnection();

			if (conn != null && !conn.isClosed()) {
				System.out.println("KET NOI SQL SERVER THANH CONG!");
				System.out.println("DATABASE: " + conn.getCatalog());
			}

			conn.close();

		} catch (Exception e) {
			System.out.println("KET NOI THAT BAI!");
			e.printStackTrace();
		}
	}

}