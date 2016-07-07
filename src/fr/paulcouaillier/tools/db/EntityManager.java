package fr.paulcouaillier.tools.db;

public class EntityManager<M extends Model> {

	private final Class<M> type;

	/**
	 * 
	 * @param type of <Model> to get a typeSafe 
	 */
	public EntityManager(Class<M> type) {
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public EntityManagerStatement<M> query() {
		return new EntityManagerStatement<M>(type);
	}

	public EntityManagerStatement<M> getOne() {
		return new EntityManagerStatement<M>(type,0,1);
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public EntityManagerStatement<M> getPage(int page) {
		return new EntityManagerStatement<M>(type, page);
	}
	
	/**
	 * 
	 * @param modelId
	 * @return
	 */
	public M get(int modelId) {
		return (new EntityManagerStatement<M>(type, modelId)).getFirst();
	}
}
