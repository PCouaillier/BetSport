package fr.borecouaillierjollanwoets.betsport.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.json.JSONString;

import fr.paulcouaillier.tools.db.Model;

public class User extends Model implements JSONString {

	// protected Integer id;
	private String firstName;
	private String lastName;
	private String email;
	
	/**
	 * foreign key {key:Bet.id, inversedBy: doneBy}
	 */
	private LinkedList<Bet> bets= new LinkedList<>();
	
	public static final String TABLE_NAME = "";

	public User() {
		super();
	}
	
	public User(Integer id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
			preparedStatement.setInt(1, this.id);
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
	}

	@Override
	public void setterPreparedStatementResultSet(ResultSet resultSet) {
		try {
			this.id = resultSet.getInt("id");
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
	
	public String toJSONString() {
		return "{\"id\"="+this.id+",\"firstName\"=\""+this.firstName+"\",\"lastName\"=\""+this.lastName+"\",\"email\"=\""+this.email+"\"}";
	}
}
