package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.impl.ProductDaoImpl;
import vn.iotstar.entity.Product;
import vn.iotstar.services.IProductService;

public class ProductServiceImpl implements IProductService {

	private IProductDao productDao=new ProductDaoImpl();

	@Override
	public void insert(Product product) {
		productDao.insert(product);
	}

	@Override
	public void update(Product product) {
		productDao.update(product);
	}

	@Override
	public void delete(int productId) throws Exception {
		productDao.delete(productId);
	}

	@Override
	public List<Product> findAll(int page, int pageSize) {
		return productDao.findAll(page, pageSize);
	}

	@Override
	public List<Product> findByName(String productName) {
		return productDao.findByName(productName);
	}

	@Override
	public List<Product> findByCategoryId(int CategoryId) {
		return productDao.findByCategoryId(CategoryId);
	}
	@Override
	public Product findById(int productId) {
		return productDao.findById(productId);
	}
	@Override
	public List<Product> findTop10NewProducts() {
		return productDao.findTop10NewProducts();
	}

	@Override
	public List<Product> findByCategoryId(int categoryId, int page, int pageSize) {
		return productDao.findByCategoryId(categoryId, page, pageSize);
	}

	@Override
	public int count() {
		return productDao.count();
	}

	@Override
	public int countByCategoryId(int categoryId) {
		return productDao.countByCategoryId(categoryId);
	}
}
