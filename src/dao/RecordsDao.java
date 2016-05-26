package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

import beans.Records;
import utils.Db;
import dao.DAO;

public class RecordsDao extends DAO<Records> {
	
	private Connection conn;
	
	public RecordsDao() {
		conn = Db.getConnection();
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

	@Override
	protected Records toBean(ResultSet rs, Records r) throws SQLException {
		if (r == null)
			r = new Records();
		r.setRecordId(rs.getInt("recordId"));
		r.setDate(rs.getDate("date"));
		r.setDuration(rs.getInt("duration"));
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
				ps.setInt(1, r.getDuration());
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