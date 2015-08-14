package firstrow.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * ConnectionDB is a connector for connecting Database
 * @author Team FirstRow
 * @version 1.0.0
 *
 */

public class ConnectionDB {
	
	/**
	 * Load JDBC Driver Class
	 * 
	 *  @since 1.0.0
	 */
	public final static String CLASSNAME = "com.mysql.jdbc.Driver";
	
	/**
	 * Connection URL
	 * 
	 * @since 1.0.0
	 */
	public final static String URL = "jdbc:mysql://localhost:3306/WEBSERVICE";
	
	/**
	 * Database user name
	 * 
	 * @since 1.0.0
	 */
	public final static String USERNAME_DB = "pomo";
	
	/**
	 * Database password
	 * 
	 * @since 1.0.0
	 */
	public final static String PASSWORD_DB = "pomoplan";
	
	/**
	 * The singleton connection.
	 * 
	 * @since 1.0.0
	 */
	private static Connection connection;
	
	/**
	 * The singleton instance.
	 * 
	 * @since 1.0.0
	 */
	private static ConnectionDB connectionDB;
	
	
	/**
	 * Get the singleton instance.
	 * 
	 * @return the singleton instance
	 * @since 1.0.0
	 */
	public static ConnectionDB getInstance() {
		if(connectionDB == null) {
			connectionDB = new ConnectionDB();
		}
		return connectionDB;
	}
	
	/**
	 * Get the singleton connection
	 * 
	 * @return the singleton connection
	 * @since 1.0.0
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Initializes the connection
	 * 
	 * @since 1.0.0
	 */
	private ConnectionDB() {
		try {
			Class.forName(CLASSNAME);
			connection = DriverManager.getConnection(URL, USERNAME_DB, PASSWORD_DB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
