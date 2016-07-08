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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }
    
    public static boolean isUserConnected(HttpServletRequest request) {
    	User user = (User)request.getAttribute("connected_user");
    	if(user != null && user.getId() != null) {
    		return true;
    	}
    	return false;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		if(LoginServlet.isUserConnected(request)) {
			printWriter.append("{\"userId\":\""+((User)request.getAttribute("connected_user")).getId()+"\"}");
		} else {
			printWriter.append("{\"error\":\"access_denied\"}");
		}
	}

	/**
	 * @see HttpServlet#doConnect(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doConnect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = (String)request.getAttribute("input_user_username");
		String password = (String)request.getAttribute("input_user_password");

		User user = (new UserEntityManager()).getByUsernamePassword(username, password);		
		request.getSession().setAttribute("connected_user", user);
		this.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doConnect(request, response);
	}

}
