package fr.borecouaillierjollantwoets.betsport.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.borecouaillierjollantwoets.betsport.entitymanager.MatchEntityManager;
import fr.borecouaillierjollantwoets.betsport.model.Match;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Match[] ms = (new MatchEntityManager()).getNextMatches();
		
		request.setAttribute("matches", ms);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
