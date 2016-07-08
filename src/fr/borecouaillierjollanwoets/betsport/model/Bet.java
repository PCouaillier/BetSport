package fr.borecouaillierjollanwoets.betsport.model;

import fr.paulcouaillier.tools.db.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.json.JSONString;
import org.postgresql.util.PGobject;

import fr.paulcouaillier.tools.db.DBHelper;
import fr.paulcouaillier.tools.db.ForeignKey;

public class Bet extends Model implements JSONString {

	// protected Integer id;

	private ForeignKey<Match> match;

	private ForeignKey<Team> winner; 

	private Integer scoreTeamOne;

	private Integer scoreTeamTwo;

	private Boolean isWinnerCorrect = null;

	private Boolean isScoreCorrect = null;

	private Integer betPoints = null;
	
	private ForeignKey<User> user;

	public final DBHelper.TABLES TABLE = DBHelper.TABLES.TABLE_BET;

	/**
	 * foreign key
	 */
	private ForeignKey<User> doneByUser;

	/**
	 * 
	 * @param match
	 * @param winner null in case of no winner  
	 * @param scoreTeamOne
	 * @param scoreTeamTwo
	 */
	public Bet(PGobject match, PGobject winner, int scoreTeamOne, int scoreTeamTwo, PGobject doneByUser) {	
		this(new ForeignKey<Match>(match), new ForeignKey<Team>(winner) , scoreTeamOne, scoreTeamTwo, new ForeignKey<User>(doneByUser));
	}

	public Bet(Match match, Team winner, int scoreTeamOne, int scoreTeamTwo, User doneByUser) {
		this(new ForeignKey<Match>(match), new ForeignKey<Team>(winner) , scoreTeamOne, scoreTeamTwo, new ForeignKey<User>(doneByUser));
	}
	public Bet(ForeignKey<Match> match, ForeignKey<Team> winner, int scoreTeamOne, int scoreTeamTwo, ForeignKey<User> doneByUser) {
		super();
		this.match = match;
		this.winner = winner;
		this.scoreTeamOne = scoreTeamOne;
		this.scoreTeamTwo = scoreTeamTwo;
		this.doneByUser = doneByUser;
	}
	
	public Match getMatch() {
		return this.match.get();
	}
	
	public Team getWinner() {
		return this.winner.get();
	}
	
	public int getScoreTeamOne() {
		return this.scoreTeamOne;
	}
	
	public int getScoreTeamTwo() {
		return this.scoreTeamTwo;
	}
	
	public Integer getPoints() {
		if(this.betPoints == null) {
			this.calculatePoints();
		}
		return betPoints;
	}
	
	public User getUser() {
		return this.doneByUser.get();
	}

	public PGobject getUserId() {
		return this.doneByUser.getId();
	}
	
	private void calculatePoints() {
		int points = 0;
		if(this.match.get().isScoreSet()) {
			if(this.isWinnerCorrect()) {
				points +=1;
			}
			if(this.isScoreCorrect()) {
				points +=3;
			}
			this.betPoints = points;
		}
	}
	
	public Boolean isWinnerCorrect() {
		return (this.isWinnerCorrect==null)? this.calculateIfWinnerIsCorrect() : this.isWinnerCorrect;
	}
	
	public Boolean isScoreCorrect() {
		return (this.isScoreCorrect==null)? this.calculateIfScoreIsCorrect() : this.isScoreCorrect;
	}
	private Boolean calculateIfScoreIsCorrect() {
		if(this.scoreTeamOne!=null && this.scoreTeamTwo!=null) {
			if(this.scoreTeamOne == this.match.get().getScoreTeamOne() &&
					this.scoreTeamTwo == this.match.get().getScoreTeamTwo()) {
				this.isScoreCorrect = true;
				return true;
			}
			this.isScoreCorrect = false;
			return false;
		}
		return null;
	}
	
	
	private Boolean calculateIfWinnerIsCorrect() {
		if(this.match.get().getWinner() == null) {
			if(this.match.get().getScoreTeamOne() != null && this.match.get().getScoreTeamOne() == this.match.get().getScoreTeamTwo()) {
				this.isWinnerCorrect = true;
				return true;
			}
			this.isWinnerCorrect = false;
			return false;
		}
		return null;
	}

	@Override
	public void setterPreparedStatement(PreparedStatement preparedStatement) {
		try {
			preparedStatement.setString(1, this.id.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			preparedStatement.setBoolean(2, this.isScoreCorrect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setBoolean(3, this.isWinnerCorrect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setObject(4, this.doneByUser.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.setObject(5, this.match.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setterPreparedStatementResultSet(ResultSet resultSet) {
		
		this.errorCatcher(resultSet, rS -> {this.id.setType(rS.getString("id"));});
		this.errorCatcher(resultSet, rS -> {this.isScoreCorrect = resultSet.getBoolean("is_score_correct");});
		this.errorCatcher(resultSet, rS -> {this.isWinnerCorrect = resultSet.getBoolean("is_winner_correct");});
		this.errorCatcher(resultSet, rS -> {this.doneByUser = new ForeignKey<>((PGobject)resultSet.getObject("user"));});
		this.errorCatcher(resultSet, rS -> {this.match = new ForeignKey<Match>((PGobject)resultSet.getObject("match"));});
	}
	
	public JSONObject toJSON() {
		return (new JSONObject())
				.append("id", this.id)
				.append("match", this.match.get().toJSON())
				.append("winner", this.winner.get().toJSON())
				.append("scoreTeamOne", this.scoreTeamOne)
				.append("scoreTeamTwo", this.scoreTeamTwo)
				.append("isWinnerCorrect", this.isWinnerCorrect)
				.append("betPoints", betPoints);
	}
	
	public String toJSONString() {
		return this.toJSON().toString();
	}
	
	public DBHelper.TABLES getTable() {
		return DBHelper.TABLES.TABLE_BET;
	}
}
