package vn.iotstar.dao;

import vn.iotstar.entity.Category;
import java.util.List;

public interface ICategoryDao {

    void insert(Category category);

    void update(Category category);

    void delete(int categoryId) throws Exception;

    Category findById(int categoryId);

    Category findByCategoryname(String name) throws Exception;

    List<Category> findAll();

    List<Category> findAll(int page, int pagesize);

    List<Category> searchByName(String catname);

    int count();
}
