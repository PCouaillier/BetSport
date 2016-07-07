package fr.borecouaillierjollanwoets.betsport.entitymanager;

import java.sql.Date;

import fr.borecouaillierjollanwoets.betsport.model.Match;
import fr.paulcouaillier.tools.db.EntityManager;

public class MatchEntityManager extends EntityManager<Match> {

	public MatchEntityManager() {
		super(Match.class);
	}
	
	/**
	 * 
	 * @return
	 */
	public Match[] getNextMatches() {
		return this.getPage(0).WithLimit(5).where((new Date(System.currentTimeMillis())).toString()+"<matchDate").run();
	}
}
