package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.RecordAnswers;
import beans.Records;
import utils.Db;

public class RecordAnswersDao extends DAO<RecordAnswers> {
	
	private Connection conn;
	
	public RecordAnswersDao() {
		conn = Db.getConnection();
	}	
	
	private final static String TABLE        = "recordAnswers";
	private final static String SELECT_QUERY = "SELECT * FROM "+TABLE+" WHERE recordId = ? AND answerId = ?";
	private final static String INSERT_QUERY = "INSERT INTO "+TABLE+"(recordId, answerId, choice) VALUES"
			+ "(?,?,?)";
	private final static String UPDATE_QUERY = ""; // TODO if needed
	private final static String DELETE_QUERY = "DELETE FROM "+TABLE+" WHERE recordId = ? and answerId = ?";

	@Override
	public String getTableName() {
		return TABLE;
	}
	
	public List<RecordAnswers> getByRecordId(int recordId) {
		List<RecordAnswers> recordAnswers = new ArrayList<RecordAnswers>();
		try {
			String sqlQuery = "SELECT * FROM recordAnswers WHERE recordId=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, recordId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				RecordAnswers tmp = new RecordAnswers(rs.getInt("recordId"), rs.getInt("answerId"), rs.getBoolean("choice"));
				recordAnswers.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return recordAnswers;
	}

	@Override
	protected RecordAnswers toBean(ResultSet rs, RecordAnswers q) throws SQLException {
		if (q == null)
			q = new RecordAnswers();
		q.setChoice(rs.getBoolean("choice"));
		q.setRecordId(rs.getInt("recordId"));
		q.setAnswerId(rs.getInt("answerId"));
		return q;
	}

	@Override
	protected PreparedStatement prepareStatementFromBean(PreparedStatement ps, RecordAnswers q, String req) throws SQLException {
		switch(req) {
			case DELETE_QUERY:
			case SELECT_QUERY:
				ps.setInt(1, q.getRecordId());
				ps.setInt(2, q.getAnswerId());
				break;
			case INSERT_QUERY:
				ps.setInt(1, q.getRecordId());
				ps.setInt(2, q.getAnswerId());
				ps.setBoolean(3, q.getChoice());
				break;
		}
		return ps;
	}

	public List<RecordAnswers> findAll(int recordId) throws SQLException {
		List<RecordAnswers> res = super.findAll();
		res.removeIf(el -> el.getRecordId()!=recordId);
		return res;
	}
	
	@Override
	protected String getSelectString() {
		return SELECT_QUERY;
	}

	@Override
	protected String getInsertString() {
		return INSERT_QUERY;
	}
	
	@Override
	protected String getUpdateString() {
		return UPDATE_QUERY;
	}
	
	@Override
	protected String getDeleteString() {
		return DELETE_QUERY;
	}
}