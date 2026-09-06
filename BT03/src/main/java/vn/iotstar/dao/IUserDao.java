package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.UserModel;

public interface IUserDao {
	List<UserModel> findAll();

	UserModel findById(int id);

	UserModel findByUserName(String username);

	void insert(UserModel user);

	void delete(int id);

}
