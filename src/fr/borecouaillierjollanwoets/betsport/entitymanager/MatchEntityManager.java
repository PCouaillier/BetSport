package fr.borecouaillierjollanwoets.betsport.entitymanager;

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
		
		//.where((new Date(System.currentTimeMillis())).toString()+"<matchDate")
		return this.getPage(0).WithLimit(1).run();
	}
}
