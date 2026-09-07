package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.services.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {
	public ICategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public List<CategoryModel> findAll() {
		// TODO Auto-generated method stub
		return categoryDao.findAll();
	}

	@Override
	public List<CategoryModel> find(String keyword) {
		// TODO Auto-generated method stub
		return categoryDao.find(keyword);
	}

	@Override
	public CategoryModel findById(int id) {
		// TODO Auto-generated method stub
		return categoryDao.findById(id);
	}

	@Override
	public void insert(CategoryModel category) {
		// TODO Auto-generated method stub
		categoryDao.insert(category);
	}

	@Override
	public void update(CategoryModel category) {
		// TODO Auto-generated method stub
		CategoryModel cate = new CategoryModel();
		cate = categoryDao.findById(category.getCategoryid());
		if (cate != null) {
			categoryDao.update(category);
		} else {
			System.out.println("Category not found");
		}

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		CategoryModel cate = new CategoryModel();
		cate = categoryDao.findById(id);
		if (cate != null) {
			categoryDao.delete(id);
		} else {
			System.out.println("Category not found");
		}

	}

}