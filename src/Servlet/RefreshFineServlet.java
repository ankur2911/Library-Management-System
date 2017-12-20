package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.ConnectionClass;

/**
 * Servlet implementation class RefreshFineServlet
 */
@WebServlet(name="RefreshFineServlet",urlPatterns={"jsp/RefreshFineServlet"})

public class RefreshFineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshFineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("inside refresh fine table servlet");
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
			//PreparedStatement ps=con.prepareStatement("select bl.Loan_id, bl.isbn, bl.Card_id, bl.Date_out, bl.Due_date, bl.Date_in from book_loans bl, fines f where Due_date < NOW() OR f.Paid=0 ;");
			PreparedStatement ps=con.prepareStatement("select * from book_loans  where (Due_date < NOW() AND Date_in is NULL) OR (Due_date < Date_in AND Date_in IS NOT NULL) ;");

			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
				
				Date d1 = new SimpleDateFormat("yyyy-M-dd").parse((String) rs.getString(5));
				String date;
				Date d2;
				if(rs.getString(6)==null) {
					System.out.println("case null of date");
				 date = new SimpleDateFormat("yyyy-M-dd").format(new Date());
				 d2 = new SimpleDateFormat("yyyy-M-dd").parse(date);

				}else {
					System.out.println("case else of date");

				d2=new SimpleDateFormat("yyyy-M-dd").parse((String) rs.getString(6));
				}
				long diff = Math.abs(d1.getTime() - d2.getTime());
				long diffDays = diff / (24 * 60 * 60 * 1000);

				System.out.println("difference in days: "+ diffDays);
				float fine= (float) (diffDays*(0.25));
				System.out.println("fine amount: "+ fine);
				
				PreparedStatement ps2=con.prepareStatement("select * from fines where Loan_id='"+rs.getString(1)+"';");
				ResultSet rs2=ps2.executeQuery();
				if(rs2.next()) {
					rs2.beforeFirst();
					System.out.println("inside 1");
				PreparedStatement ps3=con.prepareStatement("update fines set Fine_amt='"+fine+"' where Loan_id='"+rs.getString(1)+"';");
				ps3.executeUpdate();
				}else {
					System.out.println("inside 2");

				String query = " insert into fines (Loan_id, fine_amt)"
						        + " values (?, ?)";
			    PreparedStatement preparedStmt = con.prepareStatement(query);

				preparedStmt.setString (1, rs.getString(1));
			    preparedStmt.setFloat(2, fine);
			    preparedStmt.execute();
				System.out.println("inside 3");


				}
            }
			session.setAttribute("finetablerefresh","finetablerefresh");
	        response.sendRedirect("jsp/index.jsp");

		}catch (SQLException e) {
			session.setAttribute("finetablerefresh","finetablerefreshempty");

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			session.setAttribute("finetablerefresh","finetablerefreshempty");

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
