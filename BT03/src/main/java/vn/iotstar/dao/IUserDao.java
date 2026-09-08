package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.User;

public interface IUserDao {
	List<User> findAll();

	User findById(int id);

	User findByUserName(String username);

	User findByEmail(String email);

	User findByCode(String code);

	boolean existsByEmail(String email);

	void insert(User user);

	void save(User user);

	void update(User user);

	void delete(int id);
}
