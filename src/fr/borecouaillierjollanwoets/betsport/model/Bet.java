package fr.borecouaillierjollanwoets.betsport.model;

import fr.paulcouaillier.tools.db.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONString;

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
	public Bet(int match, int winner, int scoreTeamOne, int scoreTeamTwo, int doneByUser) {				
		this(new ForeignKey<Match>(match), new ForeignKey<Team>(winner) , scoreTeamOne, scoreTeamTwo, new ForeignKey<User>(doneByUser));
	}

	public Bet(Match match, Team winner, int scoreTeamOne, int scoreTeamTwo, User doneByUser) {
		this(new ForeignKey<Match>(match), new ForeignKey<Team>(winner) , scoreTeamOne, scoreTeamTwo, new ForeignKey<User>(doneByUser));
	}
	public Bet(ForeignKey<Match> match, ForeignKey<Team> winner, int scoreTeamOne, int scoreTeamTwo, ForeignKey<User> doneByUser) {
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

	public Integer getUserId() {
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
			preparedStatement.setInt(1, this.id);
			preparedStatement.setInt(2, this.match.getId());
			preparedStatement.setBoolean(3, this.isScoreCorrect);
			preparedStatement.setBoolean(4, this.isWinnerCorrect);
			preparedStatement.setInt(5, this.doneByUser.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setterPreparedStatementResultSet(ResultSet resultSet) {
		if(!(
			this.errorCatcher(resultSet, rS -> {this.id = rS.getInt("id");})
			& this.errorCatcher(resultSet, rS -> {this.match = new ForeignKey<Match>(resultSet.getInt("match"));})
			& this.errorCatcher(resultSet, rS -> {this.isScoreCorrect = resultSet.getBoolean("is_score_correct");})
			& this.errorCatcher(resultSet, rS -> {this.isWinnerCorrect = resultSet.getBoolean("is_winner_correct");})
			& this.errorCatcher(resultSet, rS -> {this.doneByUser = new ForeignKey<>(resultSet.getInt("user"));})
		)) {
			// TODO complete this
		}
	}
	
	public String toJSONString() {
		// TODO complete this
		return "{}";
	}
}
