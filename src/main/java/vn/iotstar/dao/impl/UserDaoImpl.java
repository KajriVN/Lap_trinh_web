package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.UserDao;
import vn.iotstar.model.User;

public class UserDaoImpl extends DBConnection implements UserDao {

    @Override
    public User get(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setEmail(rs.getString("email"));
                    u.setUserName(rs.getString("username"));
                    u.setFullName(rs.getString("fullname"));
                    u.setPassWord(rs.getString("password"));
                    u.setAvatar(rs.getString("avatar"));
                    u.setRoleid(rs.getInt("roleid"));
                    u.setPhone(rs.getString("phone"));
                    u.setCreatedDate(rs.getDate("createddate"));
                    u.setActive(rs.getBoolean("is_active"));
                    u.setOtpCode(rs.getString("otp_code"));
                    u.setOtpExpired(rs.getTimestamp("otp_expired"));
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setEmail(rs.getString("email"));
                    u.setUserName(rs.getString("username"));
                    u.setFullName(rs.getString("fullname"));
                    u.setPassWord(rs.getString("password"));
                    u.setAvatar(rs.getString("avatar"));
                    u.setRoleid(rs.getInt("roleid"));
                    u.setPhone(rs.getString("phone"));
                    u.setCreatedDate(rs.getDate("createddate"));
                    u.setActive(rs.getBoolean("is_active"));
                    u.setOtpCode(rs.getString("otp_code"));
                    u.setOtpExpired(rs.getTimestamp("otp_expired"));
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO users (email, username, fullname, password, avatar, roleid, phone, createddate, is_active, otp_code, otp_expired) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPassWord());
            ps.setString(5, user.getAvatar());
            ps.setInt(6, user.getRoleid());
            ps.setString(7, user.getPhone());
            ps.setDate(8, new java.sql.Date(user.getCreatedDate().getTime()));
            ps.setBoolean(9, user.isActive());
            ps.setString(10, user.getOtpCode());
            ps.setTimestamp(11, user.getOtpExpired() != null ? new java.sql.Timestamp(user.getOtpExpired().getTime()) : null);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET email=?, fullname=?, password=?, avatar=?, roleid=?, phone=?, is_active=?, otp_code=?, otp_expired=? WHERE username=?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPassWord());
            ps.setString(4, user.getAvatar());
            ps.setInt(5, user.getRoleid());
            ps.setString(6, user.getPhone());
            ps.setBoolean(7, user.isActive());
            ps.setString(8, user.getOtpCode());
            ps.setTimestamp(9, user.getOtpExpired() != null ? new java.sql.Timestamp(user.getOtpExpired().getTime()) : null);
            ps.setString(10, user.getUserName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkExistUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
