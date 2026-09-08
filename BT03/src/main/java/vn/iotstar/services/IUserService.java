package vn.iotstar.services;

import java.util.List;

import vn.iotstar.entity.User;

public interface IUserService {

	User login(String username, String password);

	User FindByUserName(String username);

	User findByUserName(String username);

	User findById(int id);

	User findByEmail(String email);

	User findByCode(String code);

	boolean existsByEmail(String email);

	void insert(User user);

	void save(User user);

	void update(User user);

	void delete(int id);

	List<User> findAll();
}
