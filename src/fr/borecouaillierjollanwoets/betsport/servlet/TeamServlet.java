package fr.borecouaillierjollanwoets.betsport.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import fr.borecouaillierjollanwoets.betsport.entitymanager.TeamEntityManager;
import fr.borecouaillierjollanwoets.betsport.model.Team;

/**
 * Servlet implementation class TeamServlet
 */
@WebServlet("/TeamServlet")
public class TeamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public TeamServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer page;
		try {
			 page = Integer.parseInt(request.getParameter("page"));
			} catch(NumberFormatException ignore) {
				page = 0;
			}

			Team[] teams = (new TeamEntityManager()).getPage(page).run();

			PrintWriter writer = response.getWriter().append("[");
			for(Team team : teams){
				writer.append("{\"id\"=\""+team.getId()+"},");
			}
			writer.append("]");
			writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        Integer id;
        String name;

        //id = Integer.parseInt(request.getParameter("page"));

        try {
        	id = Integer.parseInt(request.getParameter("id"));;
        } catch(NumberFormatException ignore) {
            throw new HTTPException(400);
        }
        name = request.getParameter("name");
        Team team = new Team(id, name);
        team.persist();
        response.getWriter().append(team.toJSONString());
	}

}
