package fr.borecouaillierjollantwoets.betsport.entitymanager;

import fr.borecouaillierjollantwoets.betsport.model.Team;
import fr.paulcouaillier.tools.db.EntityManager;

public class TeamEntityManager extends EntityManager<Team> {

	public TeamEntityManager() {
		super(Team.class);
	}
	
}
