package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;

public class UserServiceImpl implements IUserService {

	private IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = this.findByUserName(username);

		if (user != null
				&& password.equals(user.getPassword())
				&& user.getStatus() == 1) {
			return user;
		}

		return null;
	}

	@Override
	public User FindByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User findByCode(String code) {
		return userDao.findByCode(code);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userDao.existsByEmail(email);
	}

	@Override
	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(int id) {
		userDao.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
}
