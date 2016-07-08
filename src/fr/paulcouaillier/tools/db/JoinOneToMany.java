package fr.paulcouaillier.tools.db;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PGobject;

public class JoinOneToMany<M extends Model, N extends Model>{

	private final String column;
	
	private final String tableName;
	
	private final Class<N> type;
	
	private final ForeignKey<M> m;
	
	public JoinOneToMany(M mModel, Class<N> type, String tableName, String colmun) {
		this(new ForeignKey<M>(mModel), type, tableName, colmun);
	}
	public JoinOneToMany(PGobject mModel, Class<N> type, String tableName, String colmun) {
		this(new ForeignKey<M>(mModel), type, tableName, colmun);
	}
	public JoinOneToMany(ForeignKey<M> mModel, Class<N> type, String tableName, String colmun) {
		this.m = mModel;
		this.type = type;
		this.tableName = tableName;
		this.column = colmun;
	}

	public interface ModelSetterCallback<M extends Model> {
		public M run(ResultSet resultSet);
	}
	
	@SuppressWarnings("unchecked")
	public N[] get(ModelSetterCallback<N> modelSetterCallback) throws SQLException, ClassNotFoundException {

		Connection connection = DBHelper.connect();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS RESULT_SET_LENGTH, * FROM "+tableName+" WHERE ? = ?");
		preparedStatement.setString(1, this.column);
		preparedStatement.setObject(2, this.m.getId());
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();

		resultSet.first();
		int rowcount = resultSet.getInt("RESULT_SET_LENGTH");
		N[] nArray = (N[])Array.newInstance(this.type, rowcount);
		if(rowcount < 0) {
			int i = 0;
			do {
				nArray[i] = (N) modelSetterCallback.run(resultSet);
				i++;
			} while(resultSet.next());
		}

		preparedStatement.close();
		connection.close();
		
		return nArray;
		
	}
}
