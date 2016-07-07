package fr.borecouaillierjollanwoets.betsport.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import org.json.JSONObject;

import fr.borecouaillierjollanwoets.betsport.entitymanager.BetEntityManager;
import fr.borecouaillierjollanwoets.betsport.entitymanager.MatchEntityManager;
import fr.borecouaillierjollanwoets.betsport.entitymanager.UserEntityManager;
import fr.borecouaillierjollanwoets.betsport.model.Bet;
import fr.borecouaillierjollanwoets.betsport.model.Match;
import fr.borecouaillierjollanwoets.betsport.model.Team;
import fr.borecouaillierjollanwoets.betsport.model.User;
import fr.borecouaillierjollanwoets.betsport.tools.JSONLoader;

/**
 * Servlet implementation class BetServlet
 */
@WebServlet("/BetServlet")
public class BetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BetServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bet bet = null;
		
		JSONObject jsonObject = JSONLoader.load(request, response, (callbackRequest, callbackResponse, jsonObjectCallback) -> {
		
			if(((Integer)jsonObjectCallback.get("betId")) != null) {
				return true;
			} else if( ((Integer)jsonObjectCallback.get("matchId")) != null
					&& ((Integer)jsonObjectCallback.get("userId")) != null) {
				return true;
			}
			return false;
		});
		if(jsonObject != null) {
			if(jsonObject.get("betId") != null) {
				bet = (new BetEntityManager()).get((int)jsonObject.get("betId"));
			} else {
				bet = (new BetEntityManager()).get((int)(jsonObject.get("matchId")));
			}
			if(bet != null) {
				response.getWriter().append(bet.toJSONString()).close();
				return;
			}
		}
		throw new HTTPException(400);
	}

	/**
	 * {matchId, scoreTeamOne, teamsPoint, scoreTeamOne, scoreTeamTwo, winnerId}
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("USER_SESSION");
		if(user ==null || user.getId() == null) {
			throw new HTTPException(500);
		}
		
		int matchId;
		Integer teamsPoint;
		Integer scoreTeamOne;
		Integer scoreTeamTwo;
		Integer winnerId;
		try {
			matchId = Integer.parseInt(request.getParameter("matchId"));
		} catch(NumberFormatException ignore) {
			throw new HTTPException(400);
		}
		try {
			teamsPoint = Integer.parseInt(request.getParameter("teamsPoint"));
		} catch(NumberFormatException ignore) {
			teamsPoint = null;
		}
		try {
			scoreTeamOne = Integer.parseInt(request.getParameter("scoreTeamOne"));
		} catch(NumberFormatException ignore) {
			scoreTeamOne = null;
		}
		try {
			scoreTeamTwo = Integer.parseInt(request.getParameter("scoreTeamTwo"));
		} catch(NumberFormatException ignore) {
			throw new HTTPException(400);
		}
		try {
			winnerId = Integer.parseInt(request.getParameter("winnerId"));
		} catch(NumberFormatException ignore) {
			winnerId = null;
		}
		Match match = (new MatchEntityManager()).get(matchId);

		if(!match.isBetLocked()) {
			
			if(winnerId == null && teamsPoint != null) {
				scoreTeamOne = teamsPoint;
				scoreTeamTwo = teamsPoint;
			}
			
			Team team = new Team();
			if(!(new UserEntityManager()).hasAlreadyBet(user.getId(), match.getId())) {
				Bet bet = new Bet(match, team, scoreTeamOne, scoreTeamTwo, user);
				bet.persist();
			}
		}
	}
}
