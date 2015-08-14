package firstrow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import firstrow.db.UserTable;

/**
 * UserServlet is a HttpServlet for handling GET / POST request regarding the "User" Table.
 * 
 * @author FirstRow
 * @version 1.0.0
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	/**
	 * Serial Version UID. 
	 * 
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The user ID
	 * 
	 * @since 1.0.0
	 */
	private String userId;
	
	/**
	 * The user name
	 * 
	 * @since 1.0.0
	 */
	private String username;
	
	/**
	 * The password.
	 * 
	 * @since 1.0.0
	 */
	private String password;
	
	/**
	 * The WIFI option.
	 * 
	 * @since 1.0.0
	 */
	private int wifiOff;
	
	/**
	 * The blue tooth option.
	 * 
	 * @since 1.0.0
	 */
	private int blueToothOff;
	
	/**
	 * The duration of a POMODORO.
	 * 
	 * @since 1.0.0
	 */
	private int pomoDur;
	
	/**
	 * The duration of a break.
	 * 
	 * @since 1.0.0
	 */
	private int breakDur;
	
	/**
	 * Get a userTable.
	 * 
	 * @since 1.0.0
	 */
	private UserTable userTable = new UserTable();
	
	/**
	 * The PrintWriter to send data. 
	 * 
	 * @since 1.0.0
	 */
	private PrintWriter printWriter;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mode = request.getParameter("mode");
		switch(mode) {
		case "all":
			/* Get all user information and show on web*/
			response.setContentType("text/html");
			printWriter = response.getWriter();
			printWriter.println(userTable.selectAll());
			break;
		case "select":
			/* Select daily by user name and password. */
			/* If password==null, return 1 if user name already exist, return 0 if not. */
			/* If password != null, return user information  */
			username = request.getParameter("name");
			password = request.getParameter("password");
			System.out.println(username);
			System.out.println(password);
			if(password == null){
				int existing = userTable.checkUserName(username);
				System.out.println(existing);
				printWriter = response.getWriter();
				printWriter.println(existing);
			}else{
				String userInfo = userTable.selectOne(username, password);
				printWriter = response.getWriter();
				printWriter.println(userInfo);
				System.out.println(userInfo);
			}
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
			/* Insert new User into database */
			userId = request.getParameter("id");
			username = request.getParameter("name");
			password = request.getParameter("password");
			wifiOff = Integer.parseInt(request.getParameter("wifioff"));
			blueToothOff = Integer.parseInt(request.getParameter("bluetoothoff"));
			pomoDur = Integer.parseInt(request.getParameter("pomoduration"));
			breakDur = Integer.parseInt(request.getParameter("breakduration"));
			userTable.insertUser(userId, username, password, wifiOff, blueToothOff, pomoDur, breakDur);
			break;
		case "update":
			/* Update user information according to user id */
			userId = request.getParameter("id");
			username = request.getParameter("name");
			password = request.getParameter("password");
			wifiOff = Integer.parseInt(request.getParameter("wifioff"));
			blueToothOff = Integer.parseInt(request.getParameter("bluetoothoff"));
			pomoDur = Integer.parseInt(request.getParameter("pomoduration"));
			breakDur = Integer.parseInt(request.getParameter("breakduration"));
		    userTable.update(userId, username, password, wifiOff, blueToothOff, pomoDur, breakDur);
			break;
		}	
	}

	public void destroy() {
		printWriter.close();
	}
}
