package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.ConnectionClass;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet(name="AddServlet",urlPatterns={"jsp/AddServlet"})
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside add servlet, fname criteria: "+ request.getParameter("fname"));
		System.out.println("inside add servlet, lname criteria: "+ request.getParameter("lname"));

		System.out.println("inside add servlet, address criteria: "+ request.getParameter("ad"));

		System.out.println("inside add servlet, ssn criteria: "+ request.getParameter("ssn"));

		System.out.println("inside add servlet, phone criteria: "+ request.getParameter("phone"));
		
        HttpSession session=request.getSession();  

		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		// the mysql insert statement
	      String query = "insert into Borrower (Ssn, Bname, Address, Phone)"
	        + " values (?, ?, ?, ?)";
	   // create the mysql insert preparedstatement
	      PreparedStatement preparedStmt;
	      PreparedStatement preparedStmt2;

		try {
			  preparedStmt = con.prepareStatement(query);
		
			  preparedStmt.setString (1, request.getParameter("ssn"));
		
		      preparedStmt.setString (2, request.getParameter("fname")+" "+ request.getParameter("lname"));
		      preparedStmt.setString   (3, request.getParameter("ad"));
		      if(null!=request.getParameter("phone") && request.getParameter("phone")!="") {
		      preparedStmt.setString(4, request.getParameter("phone"));
		      }else {
			  preparedStmt.setString(4, request.getParameter(null));

		      }
		      
		   // execute the preparedstatement
		     preparedStmt.execute();
		     
		     
		     
		      String query2="Select card_id from Borrower where Ssn="+request.getParameter("ssn");
			  preparedStmt2 = con.prepareStatement(query2);
			  ResultSet rs=preparedStmt2.executeQuery();
			  String cardnum="";
			  while(rs.next()) {
				  cardnum=rs.getString(1);
				  System.out.println("cardnum: "+ cardnum);

			  }

		     session.setAttribute("insertflag", "success");
		     session.setAttribute("cardid", cardnum);
		     
		      } catch (SQLException e) {
				     session.setAttribute("insertflag", "fail");

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        response.sendRedirect("jsp/index.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
