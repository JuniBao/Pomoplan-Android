package firstrow.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TagTable is for executing SQL operation on table "TAG".
 * 
 * @author firstRow
 * @since 1.0.0
 */
public class TagTable {
	
	/**
	 * Object for executing SQL statement.
	 * 
	 * @since 1.0.0
	 */
private Statement statement;

	/**
	 * Initializes connection
	 * Initializes statement.
	 * 
	 * @since 1.0.0
	 */
	public TagTable() {
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
	 * Get all tag information and show on the web.
	 * 
	 * @return HTML String contains all tags
	 * @since 1.0.0
	 */
	public String selectAll() {
		String selectTagCommand = "Select * From tag";
		try {
			
			ResultSet res = statement.executeQuery(selectTagCommand);
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			builder.append("<title>Tag Table</title>");
			builder.append("</head>");
			
			builder.append("<body>");
			builder.append("<table width = \"600\" height = \"200\" border = \"1\" align=\"left\">");
			builder.append("<tr>");
			builder.append("<th align=\"center\">Tag ID</th>");
			builder.append("<th align=\"center\">Tag Name</th>");
			builder.append("<th align=\"center\">Tag Plan</th>");
			builder.append("<th align=\"center\">User ID</th>");
			builder.append("</tr>");
			
			while (res.next()) {
	            String tagID = res.getString("TAG_ID");
	            String tagName = res.getString("NAME");
	            int tagPlan = res.getInt("PLAN");
	            String userID = res.getString("USER_ID");
	            
	            builder.append("<tr>");
	            builder.append("<td>").append(tagID).append("</td>");
	            builder.append("<td>").append(tagName).append("</td>");
	            builder.append("<td>").append(tagPlan).append("</td>");
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
	 * Update record in "TAG" table.
	 * 
	 * @param userId unique ID of USER
	 * @since 1.0.0
	 */
	public String selectById(String userID) {
		String selectTagByIDCommand = "Select TAG_ID, NAME, PLAN From tag where USER_ID='" + userID + "'";
		try {
			
			ResultSet res = statement.executeQuery(selectTagByIDCommand);
			
			StringBuilder builder = new StringBuilder();
			int flag = 0;
			while (res.next()) {
				flag = 1;
	            String tagID = res.getString("TAG_ID");
	            String tagName = res.getString("NAME");
	            int tagPlan = res.getInt("PLAN");
	            
	            builder.append(tagID).append("&").append(tagName).append("&").append(tagPlan).append(";");
	            
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
	 * Update record in "TAG" table.
	 * 
	 * @param userId unique ID of USER
	 * @param tagId unique ID of TAG
	 * @param name tag name
	 * @param plan number of pomos planned in a tag
	 * @since 1.0.0
	 */
	public int updateTag(String tagID, String name, int plan, String userId) {
		String updateTagComm = "UPDATE TAG SET NAME = \"" + name + "\", PLAN = " + plan + ", USER_ID=\"" + userId + "\" WHERE TAG_ID = \"" + tagID + "\";";
		
		try {
			int res = statement.executeUpdate(updateTagComm);
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Insert new record into "TAG" table.
	 * 
	 * @param userID unique ID of USER
	 * @param tagID unique ID of TAG
	 * @param name tag name
	 * @param plan number of pomos planned in a tag
	 * @since 1.0.0
	 */
	public int insertTag(String userID, String tagID, String name, int plan) {
		String insertTagComm = "INSERT INTO TAG VALUES (\"" + tagID + "\", \"" + name + "\", " + plan + ", \"" + userID + "\");";
		
		try {
			
			int res = statement.executeUpdate(insertTagComm);
			return res;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
