package fr.borecouaillierjollanwoets.betsport.entitymanager;

import fr.borecouaillierjollanwoets.betsport.model.Match;
import fr.paulcouaillier.tools.db.EntityManager;

public class MatchEntityManager extends EntityManager<Match>{

	public MatchEntityManager() {
		super(Match.class);
	}
	
	/**
	 * 
	 * @return
	 */
	public Match[] getNextMatches() {
		/**
		  * TODO complete these method
		  */
		return new Match[0];
	}
}
