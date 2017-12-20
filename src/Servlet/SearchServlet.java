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

import VO.Searchfields;
import connection.ConnectionClass;

/**
 * Servlet implementation class SearchServlet
 */
//@WebServlet("/SearchServlet")
@WebServlet(name="SearchServlet",urlPatterns={"jsp/SearchServlet"})

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        HttpSession session=request.getSession();  

		System.out.println("inside servlet, search criteria: "+ request.getParameter("searchfieldname"));
		String sc= request.getParameter("searchfieldname");
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Searchfields searchVO = new Searchfields();
		ArrayList<String> isbn = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();

		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> available = new ArrayList<String>();

        try {
			//PreparedStatement ps=con.prepareStatement("select b.isbn, b.title, b.Available, a.name from book b, book_authors ba, authors a where b.isbn=ba.isbn AND ba.author_id= a.author_id AND (b.isbn LIKE '%"+sc+"%' OR b.title LIKE '%"+sc+"%' OR a.name LIKE '%"+sc+"%') ");
			PreparedStatement ps=con.prepareStatement("select b.isbn, b.title, b.Available, b.author from book b where (b.isbn LIKE '%"+sc+"%' OR b.title LIKE '%"+sc+"%' OR b.author LIKE '%"+sc+"%') ");

			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				isbn.add(rs.getString(1));
				title.add(rs.getString(2));
				available.add(rs.getString(3));
				if(null==rs.getString(4)) {
					name.add("");

				}else {
				String auth= (String)rs.getString(4);
				if(auth.contains("&")) {
				System.out.println("inside replace case");
				System.out.println("before replace: "+ auth);

				auth = auth.replaceAll("&amp;", ",");
				
				System.out.println("after replace: "+ auth);
				}
				name.add(auth);
				}


            }
			if(null!=isbn) {
			searchVO.setIsbn(isbn);
			searchVO.setTitle(title);
			searchVO.setName(name);
			searchVO.setAvailable(available);
			}
			
		if(null!=searchVO && !isbn.isEmpty()) {
		session.setAttribute("searchvalues", searchVO);
		session.setAttribute("searchval", "searchval");
		}
		else{
		session.setAttribute("searchval", "searchvalempty");
		}
		
		
        } catch (SQLException e) {
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
