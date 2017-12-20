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

import VO.SearchCheckinVO;
import VO.Searchfields;
import connection.ConnectionClass;

/**
 * Servlet implementation class CheckinSearch
 */
@WebServlet(name="CheckinSearch",urlPatterns={"jsp/CheckinSearch"})

public class CheckinSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckinSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("value for search in checkin : "+ request.getParameter("searchfieldnamecheckin"));
        HttpSession session=request.getSession();  

		///
		
		
		
		
		String sc= request.getParameter("searchfieldnamecheckin");
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SearchCheckinVO searchchcheckinVO = new SearchCheckinVO();
		ArrayList<String> isbn = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();

		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> checkoutBy = new ArrayList<String>();

        try {
        	System.out.println(con);
			PreparedStatement ps=con.prepareStatement("select bl.isbn, bl.card_id, b.Bname from book_loans bl, borrower b where ( bl.Card_id=b.Card_id AND (bl.isbn LIKE '%"+sc+"%' OR bl.card_id LIKE '%"+sc+"%' OR b.Bname LIKE '%"+sc+"%')) AND Date_in IS NULL");
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				isbn.add(rs.getString(1));
				//title.add(rs.getString(2));
				checkoutBy.add(rs.getString(2));
				name.add(rs.getString(3));


            }
			if(null!=isbn && null!=title && null!=name) {
				searchchcheckinVO.setIsbn(isbn);
				//searchchcheckinVO.setTitle(title);
				searchchcheckinVO.setCheckoutBy(checkoutBy);

				searchchcheckinVO.setName(name);
			}
			
		if(null!=searchchcheckinVO && !isbn.isEmpty()) {
		session.setAttribute("searchcheckinvalues", searchchcheckinVO);
		session.setAttribute("searchvalcheckin", "searchvalcheckin");
		}
		else{
		session.setAttribute("searchvalcheckin", "searchvalcheckinempty");
		}
		
		
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		
		
		////
		
		
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
