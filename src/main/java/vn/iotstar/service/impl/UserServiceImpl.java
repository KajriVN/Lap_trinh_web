package vn.iotstar.service.impl;

import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.model.User;
import vn.iotstar.service.EmailService;
import vn.iotstar.service.UserService;

import java.util.Calendar;
import java.util.Date;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = userDao.get(username);
        if (user != null && password.equals(user.getPassWord())) {
            // Kiem tra tai khoan da kich hoat chua
            if (!user.isActive()) {
                return null; // Tai khoan chua duoc kich hoat
            }
            return user;
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.get(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void register(User user) {
        userDao.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean verifyOTP(String username, String otp) {
        User user = userDao.get(username);
        if (user != null && user.getOtpCode() != null && user.getOtpExpired() != null) {
            // Kiem tra OTP va thoi gian het han
            if (user.getOtpCode().equals(otp) && new Date().before(user.getOtpExpired())) {
                // Kich hoat tai khoan
                user.setActive(true);
                user.setOtpCode(null);
                user.setOtpExpired(null);
                userDao.update(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sendOTPForRegistration(String email, String username) {
        User user = userDao.get(username);
        if (user != null) {
            String otp = EmailService.generateOTP();
            
            // Set thoi gian het han sau 5 phut
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 5);
            Date expiredTime = cal.getTime();
            
            user.setOtpCode(otp);
            user.setOtpExpired(expiredTime);
            userDao.update(user);
            
            return EmailService.sendOTP(email, otp);
        }
        return false;
    }

    @Override
    public boolean sendOTPForResetPassword(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            String otp = EmailService.generateOTP();
            
            // Set thoi gian het han sau 5 phut
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 5);
            Date expiredTime = cal.getTime();
            
            user.setOtpCode(otp);
            user.setOtpExpired(expiredTime);
            userDao.update(user);
            
            return EmailService.sendResetPasswordOTP(email, otp);
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        User user = userDao.findByEmail(email);
        if (user != null && user.getOtpCode() != null && user.getOtpExpired() != null) {
            // Kiem tra OTP va thoi gian het han
            if (user.getOtpCode().equals(otp) && new Date().before(user.getOtpExpired())) {
                user.setPassWord(newPassword);
                user.setOtpCode(null);
                user.setOtpExpired(null);
                userDao.update(user);
                return true;
            }
        }
        return false;
    }
}
