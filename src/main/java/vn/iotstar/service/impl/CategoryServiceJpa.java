package vn.iotstar.service.impl;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDaoJpa;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;

import java.util.List;

/**
 * Business service implementation for Category.
 * Wraps the JPA DAO and adds lightweight business rules:
 * - insert checks for duplicate categoryname
 * - update verifies the record exists first
 */
public class CategoryServiceJpa implements ICategoryService {

    private final ICategoryDao cateDao = new CategoryDaoJpa();

    @Override
    public void insert(Category category) {
        // Only insert if categoryname is not already taken
        Category existing = findByCategoryname(category.getCategoryname());
        if (existing == null) {
            cateDao.insert(category);
        }
    }

    @Override
    public void update(Category category) {
        Category existing = findById(category.getCategoryId());
        if (existing != null) {
            cateDao.update(category);
        }
    }

    @Override
    public void delete(int categoryId) throws Exception {
        cateDao.delete(categoryId);
    }

    @Override
    public Category findById(int categoryId) {
        return cateDao.findById(categoryId);
    }

    @Override
    public Category findByCategoryname(String name) {
        try {
            return cateDao.findByCategoryname(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Category> findAll() {
        return cateDao.findAll();
    }

    @Override
    public List<Category> findAll(int page, int pagesize) {
        return cateDao.findAll(page, pagesize);
    }

    @Override
    public List<Category> searchByName(String catname) {
        return cateDao.searchByName(catname);
    }

    @Override
    public int count() {
        return cateDao.count();
    }
}
