package fr.borecouaillierjollantwoets.betsport.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.borecouaillierjollantwoets.betsport.entitymanager.UserEntityManager;
import fr.borecouaillierjollantwoets.betsport.model.User;

/**
 * Servlet implementation class LeaderBoardServlet
 */
@WebServlet("/LeaderBoardServlet")
public class LeaderBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaderBoardServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer page;
		try {
		 page = Integer.parseInt(request.getParameter("page"));
		} catch(NumberFormatException ignore) {
			page = 0;
		}
		
		UserEntityManager userEntityManager = new UserEntityManager();

		User[] users = userEntityManager.getPage(page).orderBy("").run();

		PrintWriter writer = response.getWriter().append("[");
		for(User user : users){
			user.toJSONString();
		}
		writer.append("]");
		writer.close();
	}	

}
