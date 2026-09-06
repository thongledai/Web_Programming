package dao;

import java.util.List;

import entity.Category;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import repository.JPAConfig;

public class CategoryDao implements ICategoryDao {

	@Override
	public void insert(Category category) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(category); // Insert dữ liệu
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
	  String jpql = "SELECT COUNT(c) FROM Category c";
	  TypedQuery<Long> query = em.createQuery(jpql, Long.class);
	  Long count = query.getSingleResult();
	  return count.intValue();
  }
	@Override
	public void update(Category category) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(category); // Update dữ liệu
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
	public void delete(int catid) throws Exception {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			Category category = em.find(Category.class, catid);
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
	public Category findById(int catid) {
		EntityManager em = JPAConfig.getEntityManager();
		Category category = em.find(Category.class, catid);
		return category;
	}

	@Override
	public Category findByCategoryname(String catname) throws Exception {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT c FROM Category c WHERE c.categoryname = :catname";
		try {
			TypedQuery<Category> query = em.createQuery(jpql, Category.class);
			query.setParameter("catname", catname);
			Category category = query.getSingleResult();
			if (category != null) {
				throw new Exception("Category Name đã tồn tại");
			}
			return category;
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
	@Override
	public List<Category> searchByName(String catname) {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT c FROM Category c WHERE c.categoryname LIKE :catname";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		query.setParameter("catname", "%" + catname + "%");
		List<Category> categories = query.getResultList();
		return categories;
	}
	@Override
	public List<Category> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT c FROM Category c";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		List<Category> categories = query.getResultList();
		return categories;
	}
}