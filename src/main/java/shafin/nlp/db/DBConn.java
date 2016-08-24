package shafin.nlp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConn {

	// private static DBConn dbConnection;

	public static final String DATABASE_URL = "jdbc:mysql://localhost/job_koi?useSSL=false";
	public static final String USER = "root";
	public static final String PASSWORD = "SUSTcse";
	// public static final String PASSWORD = "1%hxr3:>WR+Ya8E";

	public final Connection connection;
	private ResultSet resultSet;
	private boolean connectedToDatabase = false;

	public static DBConn getDBConnection() {
		/*
		 * if (dbConnection != null) return dbConnection;
		 */
		try {
			return new DBConn();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DBConn() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(DBConn.DATABASE_URL, DBConn.USER, DBConn.PASSWORD);
		this.connectedToDatabase = true;
	}

	public DBConn(String url, String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(url, username, password);
		this.connectedToDatabase = true;
	}

	public ResultSet retriveResultset(PreparedStatement queryStatement) throws SQLException {
		if (!this.connectedToDatabase) {
			throw new IllegalStateException("Not Connected to Database");
		}
		return queryStatement.executeQuery();
	}

	public int executeQuery(PreparedStatement queryStatement) throws SQLException, IllegalStateException {
		if (!this.connectedToDatabase) {
			throw new IllegalStateException("Not Connected to Database");
		}
		return queryStatement.executeUpdate();
	}

	public void disconnect() {
		if (connectedToDatabase) {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				connection.close();
			} catch (SQLException sqlException) {
			} finally {
				connectedToDatabase = false;
			}
		}
	}
}
