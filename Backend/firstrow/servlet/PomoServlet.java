package firstrow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import firstrow.db.PomoTable;

/**
 * PomoServlet is a HttpServlet for handling GET / POST request regarding the "POMO" Table.
 * 
 * @author FirstRow
 * @version 1.0.0
 */
@WebServlet("/PomoServlet")
public class PomoServlet extends HttpServlet {
	/**
	 * Serial Version UID. 
	 * 
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The POMO ID.
	 * 
	 * @since 1.0.0
	 */
	private String pomoId;
	
	/**
	 * The memo.
	 * 
	 * @since 1.0.0
	 */
	private String memo;
	
	/**
	 * The completed time.
	 * 
	 * @since 1.0.0
	 */
	private String time;
	
	/**
	 * The tag ID.
	 * 
	 * @since 1.0.0
	 */
	private String tagId;
	
	/**
	 * The user ID.
	 * 
	 * @since 1.0.0
	 */
	private String userId;
	
	/**
	 * The daily ID.
	 * 
	 * @since 1.0.0
	 */
	private String dailyId;
	
	/**
	 * Get a PomoTable
	 * 
	 * @since 1.0.0
	 */
	private PomoTable pomoTable = new PomoTable();
	
	/**
	 * The PrintWriter to send data. 
	 * 
	 * @since 1.0.0
	 */
	private PrintWriter printWriter;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PomoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		switch(mode) {
		case "all":
			/* Get all POMO information and show on web */
			response.setContentType("text/html");
			printWriter = response.getWriter();
			printWriter.println(pomoTable.selectAll());		
			break;
		case "select":
			/* Select POMO by user id. format: pomoId&tagId&memo&dateId&time; */
			printWriter=response.getWriter();
			userId = request.getParameter("userid");
			printWriter.write(pomoTable.select(userId));
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
			/* Insert new POMO into database */
			pomoId = request.getParameter("id");
			memo = request.getParameter("memo");
			time = request.getParameter("time");
			tagId = request.getParameter("tagid");
			dailyId = request.getParameter("dailyid");
			userId = request.getParameter("userid");
			pomoTable.insertPomo(pomoId, memo, time, tagId, dailyId, userId);
			break;
		case "update":
			/* Update "POMO" table record according to POMO ID */
			pomoId = request.getParameter("id");
			memo = request.getParameter("memo");
			time = request.getParameter("time");
			tagId = request.getParameter("tagid");
			dailyId = request.getParameter("dailyid");
			userId = request.getParameter("userid");
			pomoTable.updatePomo(pomoId, memo, time, tagId, dailyId, userId);
		}
	}
	
	public void destroy(){
		printWriter.close();
	}

}
