package fr.paulcouaillier.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;

public class DBHelper {
	

	public static final String DRIVER 	= "org.postgresql.Driver";
	public static final String SGBD 	= "postgresql";
	public static final String DB_HOST	= "localhost";
	public static final String DB_NAME	= "BetSport";
	public static final String DB_DEFAULT_USER_NAME= "paulcouaillier";
	public static final String DB_DEFAULT_USER_PASSWORD= "";
	public static final String CONNECTION = "jdbc:"+SGBD+"://"+DB_HOST+"/"+DB_NAME;

	public static enum TABLES {
		TABLE_BET("Bet", new String[]{
				"id",
				"winner",
				"scoreTeamOne",
				"scoreTeamTwo",
				"isWinnerCorrect",
				"isScoreCorrect",
				"betPoints",
				"match",
				"user"
		}, new String[]{
				"UUID",
				"UUID",
				"Integer",
				"Integer",
				"boolean",
				"boolean",
				"Integer",
				"UUID",
				"UUID"
		}),
		TABLE_MATCH("Match", new String[]{
				"id",
				"teamOne",
				"teamTwo",
				"teamOneQuote",
				"teamTwoQuote",
				"scoreTeamOne",
				"scoreTeamTwo",
				"betLocked",
				"matchDate"
		}, new String[] {
				"UUID",
				"UUID",
				"UUID",
				"Integer",
				"Integer",
				"Integer",
				"Integer",
				"boolean",
				"date"
		}),
		TABLE_TEAM("Team", new String[]{
				"id",
				"name"
		}, new String[] {
				"UUID",
				"character varying(200)"
		}),
		TABLE_USER("table_User", new String[]{
				"id",
				"firstName",
				"lastName",
				"email",
				"username",
				"password"
		}, new String[]{
				"UUID",
				"character varying(200)",
				"character varying(200)",
				"character varying(200)",
				"character varying(200)",
				"character varying(255)",
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
		Statement statement;
        String query;

        for(TABLES table : TABLES.values()) {
            query = "CREATE TABLE " + table.TABLE_NAME + "(id";
            if(table.COLUMNS_TYPE[0] != "UUID") {
            	query = query + " Integer AUTOINCREMENT NOT NULL DEFAULT uuid_generate_v4()";
            } else {
            	query = query + " UUID NOT NULL DEFAULT uuid_generate_v4()";
            }
            for(int i=1; i < table.COLUMNS_NAME.length;i++) {
                query += "," + table.COLUMNS_NAME[i] + " " + table.COLUMNS_TYPE[i];
            }
            query += ",CONSTRAINT "+table.TABLE_NAME+"_PK PRIMARY KEY (id));";
            statement = connection.createStatement();
            try {
            	statement.execute(query);
            } catch(Exception e) {
            	System.out.println(query);
            	e.printStackTrace();
            }
            statement.close();
        }
        statement = connection.createStatement();
        try {
        	statement.execute("ALTER TABLE table_user ADD CONSTRAINT username_UNIQUE UNIQUE (username);");
        } catch(Exception e) {
        	e.printStackTrace();
        }
        try {
        	statement.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        try {
        	connection.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
