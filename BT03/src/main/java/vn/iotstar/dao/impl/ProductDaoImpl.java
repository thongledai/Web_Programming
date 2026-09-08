package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.IProductDao;
import vn.iotstar.entity.Product;

public class ProductDaoImpl implements IProductDao{
	@Override
	public void insert(Product product) {
		EntityManager em=JPAConfig.getEntityManager();
		EntityTransaction trans=em.getTransaction();
		try {
			trans.begin();
			em.persist(product);
			trans.commit();
			}catch(Exception e) {
				e.printStackTrace();
				trans.rollback();
			}
		finally {
			em.close();
		}
	}
	@Override
	public void update(Product product) {
		EntityManager em=JPAConfig.getEntityManager();
		EntityTransaction trans=em.getTransaction();
		try {
			trans.begin();
			em.merge(product);
			trans.commit();
			}catch(Exception e) {
				e.printStackTrace();
				trans.rollback();
			}
		finally {
			em.close();
		}
		
	}
	@Override
	public void delete(int productId) throws Exception {
		EntityManager em=JPAConfig.getEntityManager();
		EntityTransaction trans=em.getTransaction();
		try {
			trans.begin();
			Product product=em.find(Product.class, productId);
			if(product!=null) {
				em.remove(product);
			}
			else {
				throw new Exception("Product not found with id: " + productId);
			}
			trans.commit();
			}catch(Exception e) {
				e.printStackTrace();
				trans.rollback();
				throw e;
			}
		finally {
			em.close();
		}
		
	}
	@Override
	public List<Product> findAll(int page, int pageSize) {
		EntityManager em=JPAConfig.getEntityManager();
		String jpql="SELECT p FROM Product p";
		TypedQuery<Product> query=em.createQuery(jpql, Product.class);
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	@Override
	public List<Product> findByName(String productName) {
		EntityManager em=JPAConfig.getEntityManager();
		String jpql="SELECT p FROM Product p WHERE p.productName LIKE :productName";
		TypedQuery<Product> query=em.createQuery(jpql, Product.class);
		query.setParameter("productName", "%"+productName+"%");
		return query.getResultList();
	}
	@Override
	public List<Product> findByCategoryId(int CategoryId) {
		EntityManager em=JPAConfig.getEntityManager();
		String jpql="SELECT p FROM Product p WHERE p.category.categoryid = :CategoryId";
		TypedQuery<Product> query=em.createQuery(jpql, Product.class);
		query.setParameter("CategoryId", CategoryId);
		return query.getResultList();
	}
	@Override
	public Product findById(int productId) {
		EntityManager em=JPAConfig.getEntityManager();
		return em.find(Product.class, productId);
	}
	@Override
	public List<Product> findTop10NewProducts() {
		EntityManager em=JPAConfig.getEntityManager();
		String jpql="SELECT p FROM Product p ORDER BY p.productId DESC";
		TypedQuery<Product> query=em.createQuery(jpql, Product.class);
		query.setMaxResults(10);
		return query.getResultList();
	}
	@Override
	public List<Product> findByCategoryId(int categoryId, int page, int pageSize) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT p FROM Product p WHERE p.category.categoryid = :categoryId";
			TypedQuery<Product> query = em.createQuery(jpql, Product.class);
			query.setParameter("categoryId", categoryId);
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	@Override
	public int count() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(p) FROM Product p";
			TypedQuery<Long> query = em.createQuery(jpql, Long.class);
			return query.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}
	@Override
	public int countByCategoryId(int categoryId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(p) FROM Product p WHERE p.category.categoryid = :categoryId";
			TypedQuery<Long> query = em.createQuery(jpql, Long.class);
			query.setParameter("categoryId", categoryId);
			return query.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}

}
