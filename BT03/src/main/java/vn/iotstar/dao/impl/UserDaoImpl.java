package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.entity.User;

public class UserDaoImpl implements IUserDao {

	@Override
	public List<User> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT u FROM User u", User.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public User findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.find(User.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public User findByUserName(String username) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery(
					"SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			return query.getResultStream().findFirst().orElse(null);
		} finally {
			em.close();
		}
	}

	@Override
	public User findByEmail(String email) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery(
					"SELECT u FROM User u WHERE u.email = :email", User.class);
			query.setParameter("email", email);
			return query.getResultStream().findFirst().orElse(null);
		} finally {
			em.close();
		}
	}

	@Override
	public User findByCode(String code) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery(
					"SELECT u FROM User u WHERE u.code = :code", User.class);
			query.setParameter("code", code);
			return query.getResultStream().findFirst().orElse(null);
		} finally {
			em.close();
		}
	}

	@Override
	public void insert(User user) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			em.persist(user);
			trans.commit();
		} catch (RuntimeException e) {
			rollback(trans);
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();

			User user = em.find(User.class, id);

			if (user != null) {
				em.remove(user);
			}

			trans.commit();
		} catch (RuntimeException e) {
			rollback(trans);
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean existsByEmail(String email) {
		EntityManager em = JPAConfig.getEntityManager();

		try {
			String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";

			Long count = em.createQuery(jpql, Long.class)
					.setParameter("email", email)
					.getSingleResult();

			return count > 0;
		} finally {
			em.close();
		}
	}

	@Override
	public void save(User user) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			em.persist(user);
			trans.commit();
		} catch (RuntimeException e) {
			rollback(trans);
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(User user) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			em.merge(user);
			trans.commit();
		} catch (RuntimeException e) {
			rollback(trans);
			throw e;
		} finally {
			em.close();
		}
	}

	private void rollback(EntityTransaction trans) {
		if (trans.isActive()) {
			trans.rollback();
		}
	}
}