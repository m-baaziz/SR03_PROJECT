package dao;

import java.util.List;

import beans.User;

public interface UsersDao {
	
	public List<User> findAll();
	public List<User> findByEmail();
	public List<User> findByName();
	
	public boolean insertUser(User user);
	public boolean update(User user);
}
