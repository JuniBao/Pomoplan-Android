package firstrow.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * PomoTable is  for executing SQL operation on table "POMO".
 * 
 * @author Team FirstRow
 * @since 1.0.0
 */
public class PomoTable {
	
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
	public PomoTable() {
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
	 * Get all POMODORO information and show on the web.
	 * 
	 * @return HTML String contains all POMODORO
	 * @since 1.0.0
	 */
	public String selectAll() {
		String selectPomoCommand = "Select * From pomo";
		try {
			
			ResultSet res = statement.executeQuery(selectPomoCommand);
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			builder.append("<title>Pomodoro Table</title>");
			builder.append("</head>");
			
			builder.append("<body>");
			builder.append("<table width = \"600\" height = \"200\" border = \"1\" align=\"left\">");
			builder.append("<tr>");
			builder.append("<th align=\"center\">Pomo ID</th>");
			builder.append("<th align=\"center\">Memo</th>");
			builder.append("<th align=\"center\">Time</th>");
			builder.append("<th align=\"center\">Tag ID</th>");
			builder.append("<th align=\"center\">Date ID</th>");
			builder.append("<th align=\"center\">User ID</th>");
			builder.append("</tr>");
			
			while (res.next()) {
	            String pomoID = res.getString("POMO_ID");
	            String memo = res.getString("MEMO");
	            int time = res.getInt("TIME");
	            String tagID = res.getString("TAG_ID");
	            String dateID = res.getString("DATE_ID");
	            String userID = res.getString("USER_ID");
	            
	            builder.append("<tr>");
	            builder.append("<td>").append(pomoID).append("</td>");
	            builder.append("<td>").append(memo).append("</td>");
	            builder.append("<td>").append(time).append("</td>");
	            builder.append("<td>").append(tagID).append("</td>");
	            builder.append("<td>").append(dateID).append("</td>");
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
	 * Insert new record into "POMO" table.
	 * 
	 * @param pomoId unique ID of POMODORO
	 * @param memo The memo user writes for this specific POMODORO
	 * @param time Completed time of this POMODORO
	 * @param tagId unique tag ID associate with this POMODORO
	 * @param dateId unique date ID associate with this POMODORO
	 * @param userId unique user ID associate with this POMODORO
	 * @since 1.0.0
	 */
	public void insertPomo(String pomoId, String memo, String time, String tagId, String dateId, String userId){
		String insertPomoCommand = "INSERT INTO POMO VALUES (\"" + pomoId + "\", \"" + memo + "\", \"" + time + "\", \"" + tagId + "\", \"" + dateId + "\", \"" + userId + "\");";
		try {
			statement.executeUpdate(insertPomoCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update record in "POMO" table.
	 * 
	 * @param pomoId pomoId unique ID of POMODORO
	 * @param memo updated memo
	 * @param time updated completed time
	 * @param tagId updated tag ID
	 * @param dateId updated date ID
	 * @param userId updated user ID
	 * @since 1.0.0
	 */
	public void updatePomo(String pomoId, String memo, String time, String tagId, String dateId, String userId){
		String updatePomoCommand = "UPDATE POMO SET MEMO=\""+ memo + "\", TIME=\"" + time + "\", TAG_ID=\"" + tagId + "\", DATE_ID=\"" + dateId + "\", USER_ID=\"" + userId + "\" WHERE POMO_ID=\"" +pomoId + "\";";
		try {
			statement.executeUpdate(updatePomoCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get all Information of POMODOROs by user ID
	 * 
	 * @param userId the ID of the user we are searching information for.
	 * @return all pomoID, tagID, memo, dateID, time of POMODORO under specific userID
	 */
	public String select(String userId){
		String selectByTagCommand = "SELECT POMO_ID, MEMO, TIME, DATE_ID, TAG_ID FROM POMO WHERE USER_ID=\"" + userId + "\";";
		StringBuilder builder = new StringBuilder();
		try {
			ResultSet res = statement.executeQuery(selectByTagCommand);
			int flag = 0;
			while(res.next()){
				flag = 1;
				String pomoId = res.getString("POMO_ID");
				String memo = res.getString("MEMO");
				String time = res.getString("TIME");
				String dateId = res.getString("DATE_ID");
				String tagId = res.getString("TAG_ID");
				builder.append(pomoId).append("&").append(tagId).append("&").append(memo).append("&").append(dateId).append("&").append(time).append(";");
				System.out.println(builder.toString());
			}
			if(flag == 0){
				return "0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return builder.toString();
	}
}
