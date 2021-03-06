package fr.borecouaillierjollantwoets.betsport.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import org.postgresql.util.PGobject;

import ca.defuse.PasswordStorage;
import ca.defuse.PasswordStorage.CannotPerformOperationException;
import fr.borecouaillierjollantwoets.betsport.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PGobject id;
		PGobject pGobject = new PGobject();
        String firstName;
        String lastName;
        String email;
        String username;
        String password;

        try {
        	pGobject.setType("UUID");
        	pGobject.setValue(request.getParameter("id"));
        	id = pGobject;
        } catch(SQLException ignore) {
            throw new HTTPException(400);
        }
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        email = request.getParameter("email");
        username = request.getParameter("username");
        try {
			password = PasswordStorage.createHash(request.getParameter("password"));
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
			password = request.getParameter("password");
		}
        User user = new User(id.toString(), firstName, lastName, email, username, password);
        user.persist();
        response.getWriter().append(user.toJSONString());
	}

}
