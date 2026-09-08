package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {

	ICategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	public List<Category> find(String keyword) {
		return categoryDao.find(keyword);
	}

	@Override
	public Category findById(int id) {
		return categoryDao.findById(id);
	}

	@Override
	public void insert(Category category) {
		categoryDao.insert(category);
	}

	@Override
	public void update(Category category) {
		categoryDao.update(category);

	}

	@Override
	public void delete(int id) throws Exception {
		categoryDao.delete(id);
	}

	@Override
	public int count() {
		return categoryDao.count();
	}

	@Override
	public List<Category> findAll(int page, int pagesize) {
		return categoryDao.findAll(page, pagesize);
	}

}
