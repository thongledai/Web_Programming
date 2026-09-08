package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;

public class CategoryDaoImpl implements ICategoryDao {

	@Override
	public List<Category> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT c FROM Category c";
			TypedQuery<Category> query = em.createQuery(jpql, Category.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Category> find(String keyword) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT c FROM Category c WHERE c.categoryname LIKE :keyword";
			TypedQuery<Category> query = em.createQuery(jpql, Category.class);
			query.setParameter("keyword", "%" + keyword + "%");
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Category findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.find(Category.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public void insert(Category category) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(category);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Category category) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(category);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int id) throws Exception {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			Category category = em.find(Category.class, id);
			if (category != null) {
				em.remove(category);
			} else {
				throw new Exception("Không tìm thấy");
			}
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public int count() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(c) FROM Category c";
			TypedQuery<Long> query = em.createQuery(jpql, Long.class);
			Long count = query.getSingleResult();
			return count.intValue();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Category> findAll(int page, int pagesize) {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT c FROM Category c";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		query.setFirstResult((page - 1) * pagesize);
		query.setMaxResults(pagesize);
		List<Category> categories = query.getResultList();
		return categories;
	}
	

}
