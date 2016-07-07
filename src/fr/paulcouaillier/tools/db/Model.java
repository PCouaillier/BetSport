package fr.paulcouaillier.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Model {

	protected Integer id;

	protected final DBHelper.TABLES TABLE = null;

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
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			Statement statement = connect.createStatement();

			PreparedStatement preparedStatement = null;
			String query; 
			if(this.id == null) {
				query = "INSERT INTO "+DBHelper.DB_NAME+"."+TABLE.TABLE_NAME+" (id";

				for(int i=1; i<TABLE.COLUMNS_NAME.length; i++) {
					query += ", "+TABLE.COLUMNS_NAME[i];
				}
				query += ") values (null, ?, ?, ?, ?, ?, ?, ?);";
			} else {
				query = "UPDATE "+DBHelper.DB_NAME+"."+TABLE+" SET id=?";
				for(int i=1; i<TABLE.COLUMNS_NAME.length; i++) {
					query += ",match=?";	
				}
				query += "WHERE id="+id+";";

			}
			preparedStatement = (PreparedStatement)connect.prepareStatement(query);

			this.setterPreparedStatement(preparedStatement);

			if(this.id == null) {
				ResultSet resultSet = null;
				try{
					resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
					resultSet.first();
					this.id = resultSet.getInt("LAST_INSERT_ID()");
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if(resultSet!=null) {
						resultSet.close();
					}
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
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
