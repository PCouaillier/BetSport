package fr.borecouaillierjollanwoets.betsport.entitymanager;

import fr.borecouaillierjollanwoets.betsport.model.Team;
import fr.paulcouaillier.tools.db.EntityManager;

public class TeamEntityManager extends EntityManager<Team> {

	public TeamEntityManager() {
		super(Team.class);
	}
	
}
