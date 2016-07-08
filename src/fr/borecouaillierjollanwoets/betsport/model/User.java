package fr.borecouaillierjollanwoets.betsport.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.json.JSONObject;
import org.json.JSONString;

import fr.paulcouaillier.tools.db.DBHelper;
import fr.paulcouaillier.tools.db.Model;

public class User extends Model implements JSONString {

	// protected Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	
	/**
	 * foreign key {key:Bet.id, inversedBy: doneBy}
	 */
	private LinkedList<Bet> bets= new LinkedList<>();
	
	public static final String TABLE_NAME = "";

	public User() {
		super();
	}
	
	public User(String id, String firstName, String lastName, String email, String username, String password) {
		this();
		try {
			this.id.setValue(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}

	/**
	 * 
	 * @param bet
	 * @return
	 */
	public User addBet(Bet bet) {
		this.bets.add(bet);
		return this;
	}

	/**
	 * @param preparedStatement
	 */
	@Override
	public void setterPreparedStatement(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setObject(1, this.id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setString(2, this.firstName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setString(3, this.lastName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setString(4, this.email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setString(5, this.username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setString(6, this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setterPreparedStatementResultSet(ResultSet resultSet) {
		try {
			this.id.setValue(resultSet.getString("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.firstName = resultSet.getString("firstName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.lastName = resultSet.getString("lastName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.email = resultSet.getString("email");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject toJSON() {
		return (new JSONObject())
				.append("id", this.id.toString())
				.append("firstName", this.firstName)
				.append("lastName", this.lastName)
				.append("email", this.email)
				.append("username", this.username);
	}
	
	public String toJSONString() {
		return this.toJSON().toString();
	}
	
	public DBHelper.TABLES getTable() {
		return DBHelper.TABLES.TABLE_USER;
	}
}
