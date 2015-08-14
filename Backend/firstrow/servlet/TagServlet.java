package firstrow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import firstrow.db.TagTable;

/**
 * Servlet implementation class TagServlet
 * TagServlet is a HttpServlet for handling the GET /POST request regarding the "TAG" Table
 */
@WebServlet("/TagServlet")
public class TagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TagTable tagTable;
	private PrintWriter printWriter;
       
	private String userID, tagID, name;;
	private int plan;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagServlet() {
        super();
		tagTable = new TagTable();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		printWriter = response.getWriter();
		
		switch(mode) {
		/*select all the tag information from tag database*/
		case "all":
			response.setContentType("text/html");
			printWriter.println(tagTable.selectAll());
			
			printWriter.flush();
			break;
		/*select the infromation of tag by userid*/
		case "select" :
			userID = request.getParameter("userid");
			String tagByID = tagTable.selectById(userID); 
			
			response.setStatus(HttpServletResponse.SC_OK);
			printWriter.write(tagByID);
			
			printWriter.flush();
			break;
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		printWriter = response.getWriter();
		
		switch(mode) {
		/*insert a tag record*/
		case "insert": 
			userID = request.getParameter("userid");
			tagID = request.getParameter("id");
			name = request.getParameter("name");
			plan = Integer.parseInt(request.getParameter("plan"));
			tagTable.insertTag(userID, tagID, name, plan); // row number after insert operation, <1 is exception
			break;
		/*update a tag record by tagid*/	
		case "update" :
			tagID = request.getParameter("id");
			name = request.getParameter("name");
			plan = Integer.parseInt(request.getParameter("plan"));
			userID = request.getParameter("userid");
			tagTable.updateTag(tagID, name, plan, userID);
			break;
		}
	}
	
	/**
	 * destroy the servlet
	 */
	public void destroy() {
		printWriter.close();
	}
}
