package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import VO.FineTableVO;
import connection.ConnectionClass;

/**
 * Servlet implementation class ShowUserServlet
 */
@WebServlet(name="ShowUserServlet",urlPatterns={"jsp/ShowUserServlet"})

public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("inside fine table servlet");
        HttpSession session=request.getSession();  

		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FineTableVO finetableVO = new FineTableVO();
		ArrayList<String> isbn = new ArrayList<String>();
		ArrayList<Float> amount = new ArrayList<Float>();
		ArrayList<String> paid = new ArrayList<String>();
		ArrayList<String> borrower = new ArrayList<String>();
		ArrayList<String> loanid = new ArrayList<String>();
		ArrayList<String> datein = new ArrayList<String>();
		ArrayList<Integer> cardid = new ArrayList<Integer>();


		try {
			PreparedStatement ps=con.prepareStatement("select SUM(f.fine_amt), b.Bname, b.Card_id from fines f, borrower b, book_loans bl where f.Loan_id=bl.loan_id AND bl.card_id=b.card_id group by Card_id");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				
				amount.add(rs.getFloat(1));
				
				borrower.add(rs.getString(2));
			
				cardid.add(rs.getInt(3));
            }
			
			if(null!=isbn && null!=amount) {
				finetableVO.setFineamt(amount);
				finetableVO.setBorrower(borrower);
				finetableVO.setCardid(cardid);
				}
			
			if(null!=finetableVO && !cardid.isEmpty()) {
				session.setAttribute("userfinetableresult", finetableVO);
				System.out.println("setting value in servlet");
				session.setAttribute("userfinetable", "userfinetable");

			}else{
				System.out.println("inside else case");
				session.setAttribute("userfinetable", "finetableempty");
			}
			
	        response.sendRedirect("jsp/index.jsp");

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
