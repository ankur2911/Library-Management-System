package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.ConnectionClass;

/**
 * Servlet implementation class PayFine
 */
@WebServlet(name="PayFine",urlPatterns={"jsp/PayFine"})

public class PayFine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayFine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Loan id: "+ request.getParameter("loanid"));
		System.out.println("Payment: "+ request.getParameter("payment"));
		String loanid=request.getParameter("loanid");
		String payment=request.getParameter("payment");
		
		HttpSession session=request.getSession();  

		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			PreparedStatement ps=con.prepareStatement("Select * from fines where Loan_id="+loanid);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
            {
				float amount=rs.getFloat(2);
				System.out.println("old amount is: "+ amount);
				float newamount=(amount-Float.parseFloat(payment));
				System.out.println("new amount: "+ newamount);
				int paid=0;
				if(newamount==0.0) {
					PreparedStatement ps4=con.prepareStatement("update book_loans set Date_in=NOW() where Loan_id="+loanid+";");
					ps4.executeUpdate();
					paid=1;
				}
				System.out.println("before update");
				PreparedStatement ps2=con.prepareStatement("update fines set Fine_amt="+newamount+" where Loan_id="+loanid+";");
				ps2.executeUpdate();
				
				PreparedStatement ps3=con.prepareStatement("update fines set Paid="+paid+" where Loan_id="+loanid+";");
				ps3.executeUpdate();
				
				System.out.println("after update");

            }
			 response.setContentType("text/html;charset=UTF-8");
			 response.getWriter().write("Payment Successfull!");
		}catch(Exception e) {
			 response.setContentType("text/html;charset=UTF-8");
			 response.getWriter().write("Payment failed due to some error!");
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
