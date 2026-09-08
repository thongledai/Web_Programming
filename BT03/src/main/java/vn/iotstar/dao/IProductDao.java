package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Product;

public interface IProductDao {
	void insert(Product product);
	void update(Product product);
	void delete(int productId) throws Exception;
	List<Product> findAll(int page, int pageSize);
	List<Product> findByName(String productName);
	List<Product> findByCategoryId(int CategoryId);
	List<Product> findByCategoryId(int categoryId, int page, int pageSize);
	Product findById(int productId);
	List<Product> findTop10NewProducts();
	int count();
	int countByCategoryId(int categoryId);
}
