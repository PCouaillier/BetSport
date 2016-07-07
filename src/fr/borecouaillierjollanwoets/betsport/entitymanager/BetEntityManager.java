package fr.borecouaillierjollanwoets.betsport.entitymanager;

import fr.borecouaillierjollanwoets.betsport.model.Bet;
import fr.paulcouaillier.tools.db.EntityManager;

public class BetEntityManager extends EntityManager<Bet> {
	
	public BetEntityManager() {
		super(Bet.class);
	}
	
}
