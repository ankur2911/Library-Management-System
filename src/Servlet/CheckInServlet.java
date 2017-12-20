package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.ConnectionClass;

/**
 * Servlet implementation class CheckInServlet
 */
@WebServlet(name="CheckInServlet",urlPatterns={"jsp/CheckInServlet"})

public class CheckInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("The value of isbn: "+ request.getParameter("isbn"));
		String isbn=request.getParameter("isbn");
		String id=request.getParameter("id");
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

		String query = "Update Book_Loans set Date_in='"+sqlDate+"' where isbn='"+isbn+"' AND Card_id='"+id+"'";
		String query2="Update book set available='Yes' where isbn='"+isbn+"'";
	     try {
	    	 PreparedStatement	preparedStmt = con.prepareStatement(query);
	    	 preparedStmt.executeUpdate();
	    	 response.setContentType("text/html;charset=UTF-8");
		 response.getWriter().write("Check-in Successfull!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	     try {
	    	 PreparedStatement	preparedStmt2 = con.prepareStatement(query2);
		     preparedStmt2.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
