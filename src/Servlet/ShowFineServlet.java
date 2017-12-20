package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import VO.FineTableVO;
import connection.ConnectionClass;

/**
 * Servlet implementation class ShowFineServlet
 */
@WebServlet(name="ShowFineServlet",urlPatterns={"jsp/ShowFineServlet"})

public class ShowFineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFineServlet() {
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
			PreparedStatement ps=con.prepareStatement("select bl.isbn, f.fine_amt, f.paid, b.Bname, f.Loan_id, bl.Date_in, b.Card_id from fines f, book_loans bl, borrower b where f.Loan_id=bl.loan_id AND bl.card_id=b.card_id ");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				
				isbn.add(rs.getString(1));
				amount.add(rs.getFloat(2));
				System.out.println("rs.getString(3): "+ rs.getInt(3));
				if(1 == rs.getInt(3)) {
				paid.add("Yes");
				}else if(0 == rs.getInt(3)) {
				paid.add("No");
				}
				borrower.add(rs.getString(4));
				loanid.add(rs.getString(5));
				System.out.println("rs.getString(6): "+ rs.getString(6));
				datein.add(rs.getString(6));
				cardid.add(rs.getInt(7));
            }
			
			if(null!=isbn && null!=amount) {
				finetableVO.setIsbn(isbn);
				finetableVO.setFineamt(amount);
				finetableVO.setPaid(paid);
				finetableVO.setBorrower(borrower);
				finetableVO.setLoanID(loanid);
				finetableVO.setDatein(datein);
				finetableVO.setCardid(cardid);
				}
			
			if(null!=finetableVO && !isbn.isEmpty()) {
				session.setAttribute("finetableresult", finetableVO);
				System.out.println("setting value in servlet");
				session.setAttribute("finetable", "finetable");

			}else{
				System.out.println("inside else case");
				session.setAttribute("finetable", "finetableempty");
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
