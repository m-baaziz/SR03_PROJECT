package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// import java.util.ArrayList;
// import java.util.List;
import java.util.List;

import com.mysql.jdbc.Statement;

import beans.RecordAnswers;
import beans.Records;
import utils.Db;

public class RecordsDao extends DAO<Records> {
	
	private Connection conn;
	private RecordAnswersDao recordAnswersDao;
	
	public RecordsDao() {
		conn = Db.getConnection();
		recordAnswersDao = new RecordAnswersDao();
	}	
	
	private final static String TABLE        = "record";
	private final static String SELECT_QUERY = "SELECT * FROM "+TABLE+" WHERE recordId = ?";
	private final static String SELECT_QUERY2= "SELECT * FROM "+TABLE+" WHERE email = ? AND subject = ?";
	private final static String INSERT_QUERY = "INSERT INTO "+TABLE+"(duration, score, email, subject) VALUES"
			+ "(?,?,?,?)";
	private final static String UPDATE_QUERY = ""; // TODO if needed
	private final static String DELETE_QUERY = "DELETE FROM "+TABLE+" WHERE recordId = ?";
	
	@Override
	public String getTableName() {
		return TABLE;
	}
	
	public List<Records> getByEmail(String email) {
		List<Records> records = new ArrayList<Records>();
		try {
			String sqlQuery = "SELECT * FROM record WHERE email=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Records tmp = new Records(rs.getInt("recordId"), rs.getTime("duration"), rs.getInt("score"), rs.getString("email"), rs.getString("subject"));
				List<RecordAnswers> recordAnswers = recordAnswersDao.getByRecordId(tmp.getRecordId());
				tmp.addRecordAnswers(recordAnswers);
				records.add(tmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return records;
	}
	
	public boolean insertWithRecordAnswers(Records records) {
		try{
			String sqlQuery = "INSERT INTO record (email, subject, score, duration) "
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, records.getEmail());
			preparedStatement.setString(2, records.getSubject());
			preparedStatement.setInt(3, records.getScore());
			preparedStatement.setTime(4, records.getDuration());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (records.hasRecordAnswers() && generatedKeys.next()) {
				List<RecordAnswers> recordAnswers = records.getRecordAnswers();
				for (int i=0; i<recordAnswers.size(); i++) {
					RecordAnswers recordAnswer = recordAnswers.get(i);
					recordAnswer.setRecordId(generatedKeys.getInt(1));
					recordAnswersDao.insert(recordAnswer);
				}
			}
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Records getBestRecordBySubject(String subject) {
		try {
			String sqlQuery = "SELECT * FROM record WHERE subject=? ORDER BY score DESC LIMIT 1";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
			preparedStatement.setString(1, subject);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				Records bestRecords = new Records(rs.getInt("recordId"), rs.getTime("duration"), rs.getInt("score"), rs.getString("email"), rs.getString("subject"));
				List<RecordAnswers> recordAnswers = recordAnswersDao.getByRecordId(bestRecords.getRecordId());
				bestRecords.addRecordAnswers(recordAnswers);
				return bestRecords;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Records toBean(ResultSet rs, Records r) throws SQLException {
		if (r == null)
			r = new Records();
		r.setRecordId(rs.getInt("recordId"));
		r.setDuration(rs.getTime("duration"));
		r.setScore(rs.getInt("score"));
		r.setEmail(rs.getString("email"));
		r.setSubject(rs.getString("subject"));
		return r;
	}

	@Override
	public Records find(Records r) throws SQLException {
		if (r.getRecordId() != -1) return super.find(r);
		PreparedStatement req = super.genericRequest(r, SELECT_QUERY2);
		ResultSet res = req.executeQuery();
		if (!res.first()) {
			System.out.println("null");
			return null;
		}
		return toBean(res,r);
	}
	
	@Override
	protected PreparedStatement prepareStatementFromBean(PreparedStatement ps, Records r, String req) throws SQLException {
		switch(req) {
			case DELETE_QUERY:
			case SELECT_QUERY:
				ps.setInt(1, r.getRecordId());
				break;
			case SELECT_QUERY2:
				ps.setString(1, r.getEmail());
				ps.setString(2, r.getSubject());
				break;
			case INSERT_QUERY:
				ps.setTime(1, r.getDuration());
				ps.setInt(2, r.getScore());
				ps.setString(3, r.getEmail());
				ps.setString(4, r.getSubject());
				break;
		}
		return ps;
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