package fr.borecouaillierjollanwoets.betsport.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.json.JSONString;

import fr.paulcouaillier.tools.db.DBHelper;
import fr.paulcouaillier.tools.db.Model;

public class Team extends Model implements JSONString {
	
	// protected Integer id;
	
	private String name;

	protected final DBHelper.TABLES TABLE = DBHelper.TABLES.TABLE_TEAM;

	public Team(){}
	
	public Team(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public void setterPreparedStatement(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setInt(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setString(2, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setterPreparedStatementResultSet(ResultSet resultSet) {
		try {
			this.id = resultSet.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.name = resultSet.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toJSONString() {
		return this.toJSON().toString();
	}

	public JSONObject toJSON() {
		return (new JSONObject())
				.append("id", this.id)
				.append("name", this.name);
	}

}
