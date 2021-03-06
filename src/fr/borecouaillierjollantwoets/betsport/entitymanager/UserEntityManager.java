package fr.borecouaillierjollantwoets.betsport.entitymanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.postgresql.util.PGobject;

import ca.defuse.PasswordStorage;
import ca.defuse.PasswordStorage.CannotPerformOperationException;
import ca.defuse.PasswordStorage.InvalidHashException;
import fr.borecouaillierjollantwoets.betsport.model.User;
import fr.paulcouaillier.tools.db.DBHelper;
import fr.paulcouaillier.tools.db.EntityManager;

public class UserEntityManager extends EntityManager<User> {
	
	public UserEntityManager() {
		super(User.class);
	}
	
	public User getByUsernamePassword(String username, String password) {
		User user = this.getOne().where("username=\""+username.replaceAll("\\", "\\\\").replaceAll("\"", "\\\"")+"\"").getFirst();
		try {
			if(PasswordStorage.verifyPassword(password, user.getPassword())) {
				return user;
			}
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
		} catch (InvalidHashException e) {
			// @TODO WARN THE USER TO CHANGE HIS PASSWORD
		}
		return null; 
	}
	
	/**
	 * 
	 * @param userId
	 * @param matchId
	 * @return
	 */
	public boolean hasAlreadyBet(PGobject userId, PGobject matchId) {
		Connection connect = null;
		boolean hasAlreadyBet = false; 
		try {
			Class.forName(DBHelper.DRIVER);
			connect = DriverManager.getConnection(DBHelper.CONNECTION, DBHelper.DB_DEFAULT_USER_NAME, DBHelper.DB_DEFAULT_USER_PASSWORD);
		} catch(Exception ignore) {}
		try {
			connect.createStatement();
			PreparedStatement preparedStatement = (PreparedStatement)connect.prepareStatement("SELECT COUNT(*) AS nbr FROM "+DBHelper.TABLES.TABLE_BET.TABLE_NAME+" WHERE doneBy=? AND match =?;");
			preparedStatement.setObject(1, userId);
			preparedStatement.setObject(2, matchId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			if(resultSet.getInt("nbr")>0) {
				hasAlreadyBet = true;
			}
		} catch (Exception ignore) {
			ignore.printStackTrace();
		} finally {
			try {
				connect.close();
			} catch (Exception ignore) {
				ignore.printStackTrace();
			}
		}
		return hasAlreadyBet;
	}
	
	public User[] getTop(Integer length) {
		return this.getPage(0).WithLimit(length).orderBy("points DESC").run();
	}
}
