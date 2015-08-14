package firstrow.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DailyTable is a class for executing SQL operation on table "DAILY"
 * 
 * @author Team FirstRow 
 * @version 1.0.0
 */

public class DailyTable {
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
	public DailyTable() {
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
	 * Get all daily information and show on the web
	 * 
	 * @return HTML String contains all daily information
	 * @since 1.0.0
	 */
	public String selectAll() {
		String selectDailyCommand = "Select * From DAILY";
		try {
			
			ResultSet res = statement.executeQuery(selectDailyCommand);
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			builder.append("<title>Daily Table</title>");
			builder.append("</head>");
			
			builder.append("<body>");
			builder.append("<table width = \"600\" height = \"200\" border = \"1\" align=\"left\">");
			builder.append("<tr>");
			builder.append("<th align=\"center\">Daily ID</th>");
			builder.append("<th align=\"center\">Date</th>");
			builder.append("<th align=\"center\">Plan NO.</th>");
			builder.append("<th align=\"center\">User ID</th>");
			builder.append("</tr>");
			
			while (res.next()) {
	            String dailyId = res.getString("DAILY_ID");
	            String date = res.getString("DATE");
	            int plan = res.getInt("PLAN");
	            String userID = res.getString("USER_ID");
	            
	            builder.append("<tr>");
	            builder.append("<td>").append(dailyId).append("</td>");
	            builder.append("<td>").append(date).append("</td>");
	            builder.append("<td>").append(plan).append("</td>");
	            builder.append("<td>").append(userID).append("</td>");
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
	 * Return daily information according to userID
	 * 
	 * @param userID unique ID of user
	 * @return dailyID, date, dailyPlan
	 * @since 1.0.0
	 */
	public String selectById(String userID) {
		String selectDailyByIDCommand = "SELECT DAILY_ID, DATE, PLAN From daily Where USER_ID=\"" + userID + "\";";
		try {
			
			ResultSet res = statement.executeQuery(selectDailyByIDCommand);
			
			StringBuilder builder = new StringBuilder();
			int flag = 0;
			
			while (res.next()) {
				flag = 1;
	            String dailyID = res.getString("DAILY_ID");
	            String date = res.getString("DATE");
	            int dailyPlan = res.getInt("PLAN");
	            
	            builder.append(dailyID).append("&").append(date).append("&").append(dailyPlan).append(";");    
	        }
			if(flag == 0){
				return "0";
			}
			
			return builder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Insert new record into "DAILY" table 
	 * 
	 * @param dailyId unique ID of that date
	 * @param date selected date
	 * @param plan the plan number of POMODORO of that date
	 * @param userId unique user ID associated
	 * @since 1.0.0
	 */
	public void insertNewDaily(String dailyId, String date, int plan, String userId){
		String insertNewRecordCommand = "INSERT INTO DAILY VALUES (\"" + dailyId + "\", \"" + date + "\"," + plan + ", \"" + userId + "\");";
		try {
			statement.executeUpdate(insertNewRecordCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update record in "DAILY" table
	 * 
	 * @param dailyId unique ID to identify which record to change
	 * @param date updated date information
	 * @param plan updated plan number of POMODORO of that date
	 * @param userId unique user ID associated
	 * @since 1.0.0
	 */
	public void updateDaily(String dailyId, String date, int plan, String userId){
		String updateRecordCommand = "UPDATE DAILY SET DATE=\""+ date + "\", PLAN=" + plan + ", USER_ID=\"" + userId + "\" WHERE DAILY_ID=\"" + dailyId + "\";";
		try {
			statement.executeUpdate(updateRecordCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
