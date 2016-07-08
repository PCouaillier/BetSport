package fr.paulcouaillier.tools.db;

import org.postgresql.util.PGobject;

public class ForeignKey<M extends Model> {
	
	private M model;
	
	private PGobject modelId = setPGUUID(); 
	
	private PGobject setPGUUID() {
		PGobject uuid = new PGobject();
		uuid.setType("UUID");
		return uuid;
	}
	
	public ForeignKey(M model) {
		this.model = model;
		this.modelId = model.getId();
	}
	
	public ForeignKey(PGobject modelId) {
		this.modelId = modelId;
	}
	
	public PGobject getId() {
		if(this.modelId != null) {
			return modelId;
		} else if (this.model != null) {
			this.modelId = this.model.getId();
			return modelId;
		}
		return null;
	}
	public M get() {
		if(this.model != null) {
			return this.model;
		} else if (this.model != null) {
			return model;
		}
		return null;
	}
	public void setId(PGobject modelId) {
		this.modelId = modelId;
		if(this.model != null && this.model.getId() != this.modelId) {
			this.model = null; 
		}
	}
	public void set(M model) {
		this.model = model;
		this.modelId = model.getId();
	}
}
