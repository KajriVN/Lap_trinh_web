package vn.iotstar.dao;

import vn.iotstar.model.User;

public interface UserDao {
    User get(String username);
    User findByEmail(String email);
    void insert(User user);
    void update(User user);
    boolean checkExistUsername(String username);
    boolean checkExistEmail(String email);
}
