package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import connection.ConnectionClass;

/**
 * Servlet implementation class CheckOutServlet
 */
@WebServlet(name="CheckOutServlet",urlPatterns={"jsp/CheckOutServlet"})

public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside checkout servlet, with isbn and cardid as: "+ request.getParameter("isbn")+" "+ request.getParameter("cardid"));	
	//	HttpSession session=request.getSession();  

		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//check if user has three books issued:
		 String query1="Select count(*) from book_loans where Date_in IS NULL AND Card_id="+request.getParameter("cardid");
	     PreparedStatement preparedStmtcheck;
	     try {
			preparedStmtcheck = con.prepareStatement(query1);
			ResultSet rs=preparedStmtcheck.executeQuery();
			int count=10;
			if(rs.next()) {
				count=rs.getInt(1);
				System.out.println("count of active book loans: "+ count);
			}
	     
	     if(count<3) {
		//add tuple in book_loan
		// the mysql insert statement
	      String query = " insert into Book_Loans (isbn, card_id, date_out, due_date)"
	        + " values (?, ?, ?, ?)";
	   // create the mysql insert preparedstatement
	      PreparedStatement preparedStmt;
	      PreparedStatement preparedStmt2;
		try {
			  preparedStmt = con.prepareStatement(query);
		
			  preparedStmt.setString (1, request.getParameter("isbn"));
		
		      preparedStmt.setString (2, request.getParameter("cardid"));
		      java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

		      preparedStmt.setDate(3, sqlDate);
		     
		      
		      // future date
		      Calendar cal = Calendar.getInstance();
		      System.out.println("current date: " + cal.getTime());
		      cal.add(Calendar.DATE, 14);
		      System.out.println("14 days later: " + cal.getTime());
		      java.sql.Date javaSqlDate = new java.sql.Date(cal.getTime().getTime());

			  preparedStmt.setDate(4, javaSqlDate);

		      
		   // execute the preparedstatement
			  
		     preparedStmt.execute();
		     
		   //update the book table available to NO
		     
		     String query2= "update book set Available='No' where isbn="+request.getParameter("isbn");
		     preparedStmt2 = con.prepareStatement(query2); 
		     preparedStmt2.executeUpdate();
		     response.setContentType("text/html;charset=UTF-8");
		     response.getWriter().write("Checkout Successfull!");
		     
		     
					} catch (SQLException e1) {
						response.setContentType("text/html;charset=UTF-8");
					      response.getWriter().write("Checkout Failed!");
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		
	     }else {
	    	 response.setContentType("text/html;charset=UTF-8");
		     response.getWriter().write("Checkout Failed as the borrower has reached maximum limited of book loans!");
	     }
		      } catch (SQLException e) {
		    	  
		    	  response.setContentType("text/html;charset=UTF-8");
		      response.getWriter().write("Checkout Failed!");
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
