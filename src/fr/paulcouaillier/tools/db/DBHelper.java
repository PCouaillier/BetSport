package fr.paulcouaillier.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
import java.sql.PreparedStatement;

public class DBHelper {
	
	// TODO configure database connection
	public static final String DRIVER = "org.postgresql.Driver";
	public static final String DB_NAME= "";
	public static final String DB_DEFAULT_USER_NAME= "";
	public static final String DB_DEFAULT_USER_PASSWORD= "";
	public static final String CONNECTION = "jdbc:postgresql://localhost/"+DB_NAME;

	// TODO complete table
	public static enum TABLES {
		TABLE_USER("User", new String[]{
				"id"
		}, new String[]{
				"UUID"
		}),
		TABLE_BET("Bet", new String[]{
				"id"
		}, new String[] {
				"UUID"
		});

		public final String TABLE_NAME;
		public final String[] COLUMNS_NAME;
		public final String[] COLUMNS_TYPE;

		TABLES(String tableName, String[] columnsName, String[] columnsType) {
			this.TABLE_NAME = tableName;
			this.COLUMNS_NAME = columnsName;
			this.COLUMNS_TYPE = columnsType;
		}
	}
	public DBHelper() {
	}
	public static Connection connect() throws SQLException, ClassNotFoundException {
		Class.forName(DBHelper.DRIVER);
		return DriverManager.getConnection(DBHelper.CONNECTION,DBHelper.DB_DEFAULT_USER_NAME, DBHelper.DB_DEFAULT_USER_PASSWORD);
	}
	
	
	/**
	 * TODO change Interface type
	 * @param database
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void create() throws ClassNotFoundException, SQLException {
		Connection connection = connect();
        String query;

        for(TABLES table : TABLES.values()) {

            query = "CREATE TABLE "
                    + table.TABLE_NAME + "(_id"
                    + " integer primary key autoincrement";
            for(int i=1; i < table.COLUMNS_NAME.length;i++) {
                query += "," + table.COLUMNS_NAME[i] + " " + table.COLUMNS_TYPE[i] + " NOT NULL";
            }
            query += ");";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            statement.close();

        }
        
        PreparedStatement preparedStatement = connection.prepareStatement("insert into " + TABLES.TABLE_USER.TABLE_NAME + " (id, name, username, password) VALUES (null, ?, ? , ?)");
        preparedStatement.setString(1, "root");
        preparedStatement.setString(2, "root");
        preparedStatement.setString(3, "root");
        preparedStatement.execute();
        preparedStatement.close();

    }
}
