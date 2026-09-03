package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.IProductDao;
import vn.iotstar.entity.Product;

import java.util.List;

public class ProductDaoJpa implements IProductDao {

    @Override
    public void insert(Product product) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(product);
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
    public void update(Product product) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(product);
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
    public void delete(int productId) throws Exception {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Product product = em.find(Product.class, productId);
            if (product != null) {
                em.remove(product);
            } else {
                throw new Exception("Khong tim thay san pham");
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
    public Product findById(int productId) {
        EntityManager em = JpaConfig.getEntityManager();
        Product product = em.find(Product.class, productId);
        em.close();
        return product;
    }

    @Override
    public List<Product> findAll() {
        EntityManager em = JpaConfig.getEntityManager();
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public List<Product> findAll(int page, int pageSize) {
        EntityManager em = JpaConfig.getEntityManager();
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        List<Product> list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public int count() {
        EntityManager em = JpaConfig.getEntityManager();
        String jpql = "SELECT COUNT(p) FROM Product p";
        Long count = em.createQuery(jpql, Long.class).getSingleResult();
        em.close();
        return count.intValue();
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        EntityManager em = JpaConfig.getEntityManager();
        TypedQuery<Product> query = em.createNamedQuery("Product.findByCategory", Product.class);
        query.setParameter("categoryId", categoryId);
        List<Product> list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public List<Product> findLatest(int limit) {
        EntityManager em = JpaConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p ORDER BY p.createdDate DESC";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setMaxResults(limit);
        List<Product> list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public List<Product> searchByName(String productName) {
        EntityManager em = JpaConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p WHERE p.productName LIKE :productName";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("productName", "%" + productName + "%");
        List<Product> list = query.getResultList();
        em.close();
        return list;
    }
}
