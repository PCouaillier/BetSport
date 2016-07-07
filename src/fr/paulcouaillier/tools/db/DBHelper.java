package fr.paulcouaillier.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
import java.sql.PreparedStatement;

public class DBHelper {
	

	public static final String DRIVER = "org.postgresql.Driver";
	public static final String DB_NAME= "";
	public static final String DB_DEFAULT_USER_NAME= "";
	public static final String DB_DEFAULT_USER_PASSWORD= "";
	public static final String CONNECTION = "jdbc:postgresql://localhost/"+DB_NAME;


	public static enum TABLES {
		TABLE_BET("Bet", new String[]{
				"id",
				"winner",
				"scoreTeamOne",
				"scoreTeamTwo",
				"isWinnerCorrect",
				"isScoreCorrect",
				"betPoints"
		}, new String[]{
				"UUID",
				"UUID",
				"Integer",
				"Integer",
				"boolean",
				"boolean",
				"betPoints"
		}),
		TABLE_MATCH("Match", new String[]{
				"id",
				"teamOne",
				"teamTwo",
				"teamOneQuote",
				"teamTwoQuote",
				"betLocked"
		}, new String[] {
				"UUID",
				"UUID",
				"UUID",
				"Integer",
				"Integer",
				"boolean"
		}),
		TABLE_TEAM("Team", new String[]{
				"id",
				"name"
		}, new String[] {
				"UUID",
				"Varchar(255)"
		}),
		TABLE_USER("User", new String[]{
				"id",
				"firstName",
				"lastName",
				"email"
		}, new String[]{
				"UUID",
				"Varchar(255)",
				"Varchar(255)",
				"Varchar(255)"
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
	 * @param database
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void create() throws ClassNotFoundException, SQLException {
		Connection connection = connect();
        String query;

        for(TABLES table : TABLES.values()) {

            query = "CREATE TABLE "
                    + table.TABLE_NAME + "(_id";
            if(table.COLUMNS_TYPE[0] != "UUID") {
                    query = query + " integer primary key autoincrement";
            }
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
