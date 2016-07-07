package fr.paulcouaillier.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Model {
	
	protected Integer id;

	public final String TABLE ="";
	
	public static final String DB = "db";
	
	/**
	 * This method have to set all type for request
	 * @param preparedStatement
	 */
	public abstract void setterPreparedStatement(PreparedStatement preparedStatement);
	
	/**
	 * This method have to set all type for request
	 * @param preparedStatement
	 */
	public abstract void setterPreparedStatementResultSet(ResultSet resultSet);

	public Integer getId() {
		return id;
	}

	public void persist() {
		Connection connect = null;
		try {
			Class.forName(DBHelper.DRIVER);			
			connect = DriverManager.getConnection(DBHelper.CONNECTION,DBHelper.DB_DEFAULT_USER_NAME, DBHelper.DB_DEFAULT_USER_PASSWORD);
		} catch(Exception e) {}
		try {

			Statement statement = connect.createStatement();

			PreparedStatement preparedStatement = null;
			if(this.id == null) {
				preparedStatement = (PreparedStatement)connect.prepareStatement("INSERT INTO "+Model.DB+"."+TABLE+" (id, match, winner, scoreTeamOne, scoreTeamTwo, winnerIsCorrect, isScoreCorrect, betPoints) values (null, ?, ?, ?, ?, ?, ?, ?);");
			} else {
				preparedStatement = (PreparedStatement)connect.prepareStatement("UPDATE "+Model.DB+"."+TABLE+" SET match=?, winner=?, scoreTeamOne=?, scoreTeamTwo=?, winnerIsCorrect=?, isScoreCorrect=?, betPoints=? WHERE id="+id+";");
			}

			this.setterPreparedStatement(preparedStatement);

			if(this.id == null) {
				ResultSet resultSet = null;
				try{
					resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
					resultSet.first();
					this.id = resultSet.getInt("LAST_INSERT_ID()");
				} catch(Exception e) {
					resultSet.close();
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				connect.close();
			} catch (Exception e) {
				
			}
		}
	}
	
	@FunctionalInterface
	public interface Setter {
		public void set(ResultSet resultSet) throws SQLException;
	}
	
	protected boolean errorCatcher(ResultSet resultSet, Setter setterCallback) {
		try {
			setterCallback.set(resultSet);
			return false;
		}
		catch(SQLException exception) {
			exception.printStackTrace();
		}
		return true;
	}
}
