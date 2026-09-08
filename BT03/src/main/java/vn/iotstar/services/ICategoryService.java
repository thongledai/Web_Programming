package vn.iotstar.services;

import java.util.List;

import vn.iotstar.entity.Category;

public interface ICategoryService {

	List<Category> findAll();
	
	List<Category> findAll(int page, int pagesize);

	List<Category> find(String keyword);

	Category findById(int id);

	void insert(Category category);

	void update(Category category);

	void delete(int id) throws Exception;

	int count();
}