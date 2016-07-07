package fr.borecouaillierjollanwoets.betsport.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.json.JSONString;

import fr.paulcouaillier.tools.db.DBHelper;
import fr.paulcouaillier.tools.db.ForeignKey;
import fr.paulcouaillier.tools.db.Model;

public class Match extends Model implements JSONString {

	private Integer id;
	
	private ForeignKey<Team> teamOne;
	
	private ForeignKey<Team> teamTwo;
	
	private ForeignKey<Team> winner;
	
	private Integer scoreTeamOne;
	
	private Integer scoreTeamTwo;
	
	private Double teamOneQuote;
	
	private Double teamTwoQuote;
	
	private boolean betLocked = true;
	
	private Date matchDate;
	
	protected final DBHelper.TABLES TABLE = DBHelper.TABLES.TABLE_MATCH;

	public Match() {
	}
	
	public Match(Integer id) {
		this.id = id;
	}
	
	public Match setTeamOne(Team team) {
		this.teamOne = new ForeignKey<>(team);
		return this;
	}
	
	public Integer getId() {
		return this.id;
	}

	public Team getTeamOne() {
		return this.teamOne.get();
	}

	public Match setTeamTwo(Team team) {
		this.teamTwo = new ForeignKey<>(team);
		return this;
	}

	public Team getTeamTwo() {
		return this.teamTwo.get();
	}
	
	public Match setWinner(Team winner) {
		this.winner = new ForeignKey<>(winner);
		return this;
	}
	
	public Team getWinner() {
		return this.winner.get();
	}

	public Match setScoreTeamOne(Integer scoreTeamOne) {
		this.scoreTeamOne = scoreTeamOne;
		return this;
	}

	public Integer getScoreTeamOne() {
		return this.scoreTeamOne;
	}

	public Match setScoreTeamTwo(Integer scoreTeamTwo) {
		this.scoreTeamTwo = scoreTeamTwo;
		return this;
	}

	public Integer getScoreTeamTwo() {
		return this.scoreTeamTwo;
	}

	public Match setTeamOneQuote(Double teamOneQuote){
		this.teamOneQuote = teamOneQuote;
		return this;
	}
	
	public Double getTeamOneQuote() {
		return this.teamOneQuote;
	}
	
	public Match setTeamTwoQuote(Double teamTwoQuote){
		this.teamTwoQuote = teamTwoQuote;
		return this;
	}
	
	public Double getTeamTwoQuote() {
		return this.teamTwoQuote;
	}
	
	public boolean isScoreSet() {
		if(this.scoreTeamOne == null || this.scoreTeamTwo == null ||
				this.winner == null && this.scoreTeamOne != this.scoreTeamTwo) {
			return false;
		}
		return true;
	}
	
	public boolean isBetLocked() {
		return this.betLocked;
	}
	public Match setBetLocked(boolean betLocked) {
		this.betLocked = betLocked;
		return this;
	}

	@Override
	public void setterPreparedStatementResultSet(ResultSet resultSet) {
		try {this.id 			= resultSet.getInt("id");			  			} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.teamOne 		= new ForeignKey<>(resultSet.getInt("teamOne"));} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.teamTwo 	=	 new ForeignKey<>(resultSet.getInt("teamTwo"));	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.scoreTeamOne 	= resultSet.getInt("scoreTeamOne"); 			} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.scoreTeamTwo 	= resultSet.getInt("scoreTeamTwo");				} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.teamOneQuote 	= resultSet.getDouble("teamOneQuote");			} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.teamTwoQuote 	= resultSet.getDouble("teamTwoQuote");			} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {this.matchDate 	= resultSet.getDate("matchDate");				} catch (SQLException sqlException) {sqlException.printStackTrace();}
	}

	@Override
	public void setterPreparedStatement(PreparedStatement preparedStatement) {
		try {preparedStatement.setInt(1, this.id);				} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setInt(2, this.teamOne.getId());	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setInt(3, this.teamTwo.getId());	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setInt(4, this.scoreTeamOne);	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setInt(5, this.scoreTeamTwo);	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setDouble(6, this.teamOneQuote);	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setDouble(7, this.teamTwoQuote);	} catch (SQLException sqlException) {sqlException.printStackTrace();}
		try {preparedStatement.setDate(8, this.matchDate);		} catch (SQLException sqlException) {sqlException.printStackTrace();}
	}

	@Override
	public String toJSONString() { 
		return this.toJSON().toString();
	}

	public JSONObject toJSON() {
		return (new JSONObject())
				.append("id", 			this.id)
				.append("teamOne", 		this.teamOne.get().toJSON())
				.append("teamTwo", 		this.teamTwo.get().toJSON())
				.append("scoreTeamOne", this.scoreTeamOne)
				.append("scoreTeamTwo", this.scoreTeamTwo)
				.append("teamOneQuote", this.teamOneQuote)
				.append("teamTwoQuote", this.teamTwoQuote)
				.append("matchDate", 	this.matchDate);
	}
	
}
