package fr.paulcouaillier.tools.db;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EntityManagerStatement<M extends Model> {

		private static final int DEFAULT_PAGE_SIZE = 25;
	
		private int page = 0;
		private int pageSize = DEFAULT_PAGE_SIZE;
		private String where = "";
		private String order = null;

		private final Class<M> type;

		public EntityManagerStatement(Class<M> type, int page, int pageSize) {
			this.type = type;
			this.page = page;
			this.pageSize = pageSize;
			new DBHelper();
		}
		
		/**
		 * 
		 * @param type
		 * @param page
		 */
		public EntityManagerStatement(Class<M> type, int page) {
			this(type, page, EntityManagerStatement.DEFAULT_PAGE_SIZE);
		}
		
		/**
		 * 
		 * @param type
		 */
		public EntityManagerStatement(Class<M> type) {
			this(type, 0, EntityManagerStatement.DEFAULT_PAGE_SIZE);
		}
		
		public Connection connect() throws SQLException, ClassNotFoundException {
			Class.forName(DBHelper.DRIVER);
			return DriverManager.getConnection(DBHelper.CONNECTION, DBHelper.DB_DEFAULT_USER_NAME, DBHelper.DB_DEFAULT_USER_PASSWORD);
		}

		/**
		 * 
		 * @param pageSize
		 * @return
		 */
		public EntityManagerStatement<M> WithLimit(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}
		
		public EntityManagerStatement<M> where(String where) {
			this.where = where;
			return this;
		}
		
		public EntityManagerStatement<M> orderBy(String order) {
			this.order = order;
			return this;
		}

		public M getFirst() {
			Connection connect;
			M m;
			try {
				m = this.type.newInstance();
				connect = this.connect();
				ResultSet resultSet = connect.prepareStatement("SELECT * FROM "+m.getTable().TABLE_NAME+" "+where+";").executeQuery();
				if(resultSet.first()) {
					m.setterPreparedStatementResultSet(resultSet);
					return m;
				}
				return null;
			} catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException exception) {
				exception.printStackTrace();
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		public M[] run() {
			Connection connect = null;
			M m;
			M[] mArray;
			LinkedList<M> mList = new LinkedList<>();
			int i = 0;
			try {
				m = this.type.newInstance(); // Temporary M object to call m.TABLE_NAME
				connect = this.connect();
				String query = "SELECT ";
				for(String tableName : m.getTable().COLUMNS_NAME) {
					query += tableName + " AS " + tableName + ", ";
				}
				query = (String) query.subSequence(0, query.length()-2);
				query+=" FROM "+m.getTable().TABLE_NAME;
				if(where !=null && !where.equals("")) {
					query +=" WHERE "+where;
				}
				query += " OFFSET "+this.page*this.pageSize+" LIMIT "+this.pageSize;
				if(this.order != null && !	this.order.equals("")) {
					query += " ORDER BY "+this.order;
				}
				query += ";"; 
				System.out.println(query);
				ResultSet resultSet = connect.prepareStatement(query).executeQuery();
				
				mArray = (M[]) Array.newInstance(this.type, pageSize);
				while(i<this.pageSize && resultSet.next()) {
					m = this.type.newInstance();
					m.setterPreparedStatementResultSet(resultSet);
					mList.add(m);
					i++;
				}
				if(pageSize<mList.size()) {
					mArray = (M[]) Array.newInstance(this.type, pageSize);
				} else {
					mArray = (M[]) Array.newInstance(this.type, mList.size());
				}
				//return mArray;
				i = 0;
				for(M model : mList) {
					mArray[i] = model;
					i++;
				}
				return mArray;
			} catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
			return (M[]) Array.newInstance(this.type, 0);
		}
	}