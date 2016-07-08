package fr.paulcouaillier.tools.db;

import java.lang.reflect.ParameterizedType;
import org.postgresql.util.PGobject;


public class ForeignKey<M extends Model> {
	
	private M model;
	
	private Class<M> type;
	
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
	
	public ForeignKey<M> setType(Class<M> type) {
		this.type = type;
		return this;
	}
	
	public ForeignKey(PGobject modelId) {
		this.modelId = modelId;
	}
	
	public PGobject getId() {
		if(this.modelId != null) {
			return modelId;
		} else if (this.model != null) {
			this.modelId = this.model.getId();
			return this.modelId;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public M get() {
		if(this.model != null) {
			return this.model;
		} else if (this.model == null && this.modelId !=null) {
			M model;
			try {
				model = (M) this.type.newInstance();
				EntityManagerStatement<M> entityManagerStatement = new EntityManagerStatement<>((Class<M>)model.getClass());
				model = entityManagerStatement.where("id="+this.getId().toString()).getFirst();
				if(model != null) {
					this.model = model;
					this.modelId = this.model.getId();
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println(this.model);
			return this.model;
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
