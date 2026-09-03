package vn.iotstar.service;

import vn.iotstar.model.User;

public interface UserService {
    User login(String username, String password);
    User findByUsername(String username);
    User findByEmail(String email);
    void register(User user);
    void updateUser(User user);
    boolean checkExistUsername(String username);
    boolean checkExistEmail(String email);
    boolean verifyOTP(String username, String otp);
    boolean sendOTPForRegistration(String email, String username);
    boolean sendOTPForResetPassword(String email);
    boolean resetPassword(String email, String otp, String newPassword);
}
