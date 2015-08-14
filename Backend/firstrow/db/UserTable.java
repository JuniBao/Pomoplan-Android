package firstrow.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * UserTable is  for executing SQL operation on table "User".
 * 
 * @author Team FirstRow
 * @since 1.0.0
 */
public class UserTable {

	/**
	 * Object for executing SQL statement.
	 * 
	 * @since 1.0.0
	 */
	private Statement statement;
	
	/**
	 * Initializes statement.
	 * 
	 * @since 1.0.0
	 */
	public UserTable() {
		Connection connection = ConnectionDB.getInstance().getConnection();
		if(connection == null) {
			System.out.println("connection null");
		}
		
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Get all user information and show on the web.
	 * 
	 * @return HTML String contains all user.
	 * @since 1.0.0
	 */
	public String selectAll() {
		String selectUserCommand = "Select * From USER";
		try {
			
			ResultSet res = statement.executeQuery(selectUserCommand);
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			builder.append("<title>User Table</title>");
			builder.append("</head>");
			
			builder.append("<body>");
			builder.append("<table width = \"600\" height = \"200\" border = \"1\" align=\"left\">");
			builder.append("<tr>");
			builder.append("<th align=\"center\">User ID</th>");
			builder.append("<th align=\"center\">User Name</th>");
			builder.append("<th align=\"center\">Password</th>");
			builder.append("<th align=\"center\">Wifi Off</th>");
			builder.append("<th align=\"center\">Blue tooth Off</th>");
			builder.append("<th align=\"center\">Pomodoro Duration(min)</th>");
			builder.append("<th align=\"center\">Break Duration(min)</th>");
			builder.append("</tr>");
			
			while (res.next()) {
	            String userID = res.getString("USER_ID");
	            String userName = res.getString("USERNAME");
	            String password = res.getString("PASSWORD");
	            
	            int wifiOFF = res.getInt("WIFIOFF");
	            int bluetoothOFF = res.getInt("BLUETOOTHOFF");
	            int pomoDur = res.getInt("POMODURATION");
	            int breakDur = res.getInt("BREAKDURATION");
	            
	            builder.append("<tr>");
	            builder.append("<td>").append(userID).append("</td>");
	            builder.append("<td>").append(userName).append("</td>");
	            builder.append("<td>").append(password).append("</td>");
	            builder.append("<td>").append(wifiOFF).append("</td>");
	            builder.append("<td>").append(bluetoothOFF).append("</td>");
	            builder.append("<td>").append(pomoDur).append("</td>");
	            builder.append("<td>").append(breakDur).append("</td>");
	            builder.append("</tr>");
	        }
			
			builder.append("</table>").append("</body>").append("</html>");
			
			return builder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Select one user according to user name and password.
	 * 
	 * @param username: user name user entered.
	 * @param password: password user entered.
	 * @return "0" if no such user exists, userID; WIFI, blue-tooth, POMO duration, break duration if user exists. 
	 * @since 1.0.0
	 */
	public String selectOne(String username, String password) {
		String selectUserCommand = "Select USER_ID, WIFIOFF, BLUETOOTHOFF, POMODURATION, BREAKDURATION From USER Where USERNAME=\"" +  username + "\" And PASSWORD=\"" + password + "\";";
		System.out.println(selectUserCommand);
		try {
			
			ResultSet res = statement.executeQuery(selectUserCommand);
			if(res.next() == false){
				return "0";
			}
			
			StringBuilder builder = new StringBuilder();
			String userID = res.getString("USER_ID");
	        int wifiOFF = res.getInt("WIFIOFF");
	        int bluetoothOFF = res.getInt("BLUETOOTHOFF");
	        int pomoDur = res.getInt("POMODURATION");
	        int breakDur = res.getInt("BREAKDURATION");
	          
	        builder.append(userID).append("&").append(wifiOFF).append("&").append(bluetoothOFF).append("&").append(pomoDur).append("&").append(breakDur).append("\n");
			
			return builder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Check whether user name has already exists in database when user register.
	 * 
	 * @param username: user entered user name.
	 * @return 1 if user name already exists, 0 if user name did not exist.
	 * @since 1.0.0
	 */
	public int checkUserName(String username){
		String checkUserCommand = "SELECT COUNT(*) FROM user WHERE username=\"" + username + "\";";
		ResultSet res = null;
		int count = 0;
		try {
			res = statement.executeQuery(checkUserCommand);
			res.next();
			count = res.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * Insert new record to "USER" table.
	 * 
	 * @param userId Unique ID of user.
	 * @param username Unique User name. 
	 * @param password User entered password.
	 * @param wifiOff WIFI Option: 0 on, 1 off. 
	 * @param blueToothOff Blue-tooth option: 0 on, 1 off.
	 * @param pomoDur User selected duration of one POMODORO in minutes. 
	 * @param breakDur USer selected duration of one break in minutes
	 * @since 1.0.0
	 */
	public void insertUser(String userId, String username, String password, int wifiOff, int blueToothOff, int pomoDur, int breakDur){
		String insertUserCommand = "INSERT INTO USER VALUES (\"" + userId + "\",  \"" + username+ "\",  \"" + password + "\", " + wifiOff + "," + blueToothOff + "," + pomoDur + "," + breakDur + ");";
		
		try {
			statement.executeUpdate(insertUserCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update user Information.
	 * @param userId ID of the user we would like to update information for.
	 * @param username Updated user name.
	 * @param password Updated password.
	 * @param wifiOff Updated WIFI option.
	 * @param bluetoothOff Updated blue-tooth option.
	 * @param pomoDur Updated POMO duration length.
	 * @param breakDur Updated break duration length.
	 * @version 1.0.0
	 */
	public void update(String userId, String username, String password, int wifiOff, int bluetoothOff, int pomoDur, int breakDur){
		String updateSettingCommand = "UPDATE USER SET USERNAME=\"" + username + "\", PASSWORD=\"" + password + "\", WIFIOFF=" + wifiOff + ", BLUETOOTHOFF=" + bluetoothOff + ", POMODURATION=" + pomoDur + ", BREAKDURATION=" + breakDur + " WHERE USER_ID=\"" + userId + "\";";
		try {
			statement.executeUpdate(updateSettingCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
