package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.models.Category;

public class CategoryDaoImpl implements ICategoryDao {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(
                    "ServletCRUDMVCPU"
            );


    @Override
    public void insert(Category category) {

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            em.persist(category);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {

                transaction.rollback();
            }

            e.printStackTrace();

            throw e;

        } finally {

            em.close();
        }
    }


    @Override
    public void edit(Category category) {

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            em.merge(category);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {

                transaction.rollback();
            }

            e.printStackTrace();

            throw e;

        } finally {

            em.close();
        }
    }


    @Override
    public void delete(int id) {

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            Category category =
                    em.find(
                            Category.class,
                            id
                    );

            if (category != null) {

                em.remove(category);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {

                transaction.rollback();
            }

            e.printStackTrace();

            throw e;

        } finally {

            em.close();
        }
    }



    @Override
    public Category get(int id) {

        EntityManager em =
                emf.createEntityManager();

        try {

            return em.find(
                    Category.class,
                    id
            );

        } finally {

            em.close();
        }
    }


    @Override
    public Category get(String name) {

        EntityManager em =
                emf.createEntityManager();

        try {

            String jpql =
                    "SELECT c " +
                    "FROM Category c " +
                    "WHERE c.name = :name";

            TypedQuery<Category> query =
                    em.createQuery(
                            jpql,
                            Category.class
                    );

            query.setParameter(
                    "name",
                    name
            );

            query.setMaxResults(1);

            List<Category> list =
                    query.getResultList();

            if (list.isEmpty()) {

                return null;
            }

            return list.get(0);

        } finally {

            em.close();
        }
    }


    @Override
    public List<Category> getAll() {

        EntityManager em =
                emf.createEntityManager();

        try {

            TypedQuery<Category> query =
                    em.createNamedQuery(
                            "Category.findAll",
                            Category.class
                    );

            return query.getResultList();

        } finally {

            em.close();
        }
    }



    @Override
    public List<Category> search(
            String keyword
    ) {

        EntityManager em =
                emf.createEntityManager();

        try {

            String jpql =
                    "SELECT c " +
                    "FROM Category c " +
                    "WHERE LOWER(c.name) " +
                    "LIKE LOWER(:keyword)";

            TypedQuery<Category> query =
                    em.createQuery(
                            jpql,
                            Category.class
                    );

            query.setParameter(
                    "keyword",
                    "%" + keyword + "%"
            );

            return query.getResultList();

        } finally {

            em.close();
        }
    }
}