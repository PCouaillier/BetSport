package fr.borecouaillierjollantwoets.betsport.entitymanager;

import fr.borecouaillierjollantwoets.betsport.model.Bet;
import fr.paulcouaillier.tools.db.EntityManager;

public class BetEntityManager extends EntityManager<Bet> {
	
	public BetEntityManager() {
		super(Bet.class);
	}
	
}
