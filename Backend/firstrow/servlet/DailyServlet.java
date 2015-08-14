package firstrow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import firstrow.db.DailyTable;

/**
 * DailyServlet is a HttpServlet for handling GET / POST request regarding the "DAILY" Table.
 * 
 * @author FirstRow
 * @version 1.0.0
 */

@WebServlet("/DailyServlet")
public class DailyServlet extends HttpServlet {
	/**
	 * Serial Version UID. 
	 * 
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The dailyId.
	 * 
	 * @since 1.0.0
	 */
	private String dailyId;
	
	/**
	 * The date.
	 * 
	 * @since 1.0.0
	 */
	private String date;
	
	/**
	 * The plan number of POMODORO of that date.
	 * 
	 * @since 1.0.0
	 */
	private int plan;
	
	/**
	 * The user ID.
	 * 
	 * @since 1.0.0
	 */
	private String userId;
	
	/**
	 * Get a daily table.
	 * 
	 * @since 1.0.0
	 */
	private DailyTable dailyTable = new DailyTable();
	
	/**
	 * The PrintWriter to send data. 
	 * 
	 * @since 1.0.0
	 */
	private PrintWriter printWriter;
       
	
	 /**
     * @see HttpServlet#HttpServlet()
     */
    public DailyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		
		switch(mode) {
		case "all":		
			/* Get all daily information and show on web */
			response.setContentType("text/html");
			printWriter = response.getWriter();
			printWriter.println(dailyTable.selectAll());
			break;
		case "select":
			/* Select daily by user id. format: dailyID&date&dailyPlan; */
			String userID = request.getParameter("userid");
			String dailys = dailyTable.selectById(userID);
			printWriter = response.getWriter();
			printWriter.write(dailys);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		
		switch(mode) {
		case "insert":
			/* Insert new daily into database */
			dailyId = request.getParameter("id");
			date = request.getParameter("date");
			plan = Integer.parseInt(request.getParameter("plan"));
			userId = request.getParameter("userid");
			dailyTable.insertNewDaily(dailyId, date, plan, userId);
			break;
		case "update":
			/* Update "DAILY" table record according to daily ID */
			dailyId = request.getParameter("id");
			date = request.getParameter("date");
			plan = Integer.parseInt(request.getParameter("plan"));
			userId = request.getParameter("userid");
			dailyTable.updateDaily(dailyId, date, plan, userId);
			break;
		}
	}
	
	public void destroy(){
		printWriter.close();
	}

}
