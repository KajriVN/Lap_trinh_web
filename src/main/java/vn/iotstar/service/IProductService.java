package vn.iotstar.service;

import vn.iotstar.entity.Product;
import java.util.List;

public interface IProductService {
    void insert(Product product);
    void update(Product product);
    void delete(int productId) throws Exception;
    Product findById(int productId);
    List<Product> findAll();
    List<Product> findAll(int page, int pageSize);
    int count();
    List<Product> findByCategory(int categoryId);
    List<Product> findLatest(int limit);
    List<Product> searchByName(String productName);
}
