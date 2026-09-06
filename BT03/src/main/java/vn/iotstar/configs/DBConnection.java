package vn.iotstar.configs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private final String serverName = "localhost";
	private final String dbName = "WebProgramming"; // align with persistence.xml
	private final String portNumber = "1433";
	private final String instance = ""; // optional instance from persistence.xml
	private final String userID = "sa";
	private final String password = "123"; // match persistence.xml credentials

	public Connection getConnection() throws Exception {
		// use the same connection settings as persistence.xml
		// include instanceName if provided
		String url = "jdbc:sqlserver://localhost";
		if (instance != null && !instance.isBlank()) {
			url += ";instanceName=" + instance;
		}
		url += ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true";

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