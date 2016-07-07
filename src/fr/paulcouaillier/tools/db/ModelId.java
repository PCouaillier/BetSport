package fr.paulcouaillier.tools.db;

public class ModelId<T> {
	
	private T id;
	
	public ModelId(T id) {
		this.id = id;
	}

	public T getId() {
		return this.id;
	}
}
