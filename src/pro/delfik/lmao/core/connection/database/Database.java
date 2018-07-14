package pro.delfik.lmao.core.connection.database;

import lib.I;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static Connection connection;
	private static String host, database, username, password;
	private static int port;
	
	public static void enable() {
		host = "localhost";
		port = 3306;
		database = "LmaoNetwork";
		username = "minecraft";
		password = "MedvedLubitCookie";
		try {
			openConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static void openConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) return;
		I.delay(() -> {
			try {
				if (connection != null && !connection.isClosed()) return;
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}, 0);
	}
	
	public static Result sendQuery(String query) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet set = statement.executeQuery(query);
			return new Result(statement, set);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				statement.close();
			} catch (Exception ignored) {}
			return null;
		}
	}
	
	public static int sendUpdate(String update) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(update);
		statement.close();
		return result;
	}
	
	public static class Result {
		public final Statement st;
		public final ResultSet set;
		
		public Result(Statement st, ResultSet set) {
			this.st = st;
			this.set = set;
		}
	}
}
