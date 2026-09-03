package vn.iotstar.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        String alertMsg = "";

        if (userService.checkExistUsername(username)) {
            alertMsg = "Ten dang nhap da ton tai!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }

        if (userService.checkExistEmail(email)) {
            alertMsg = "Email da duoc su dung!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        user.setEmail(email);
        user.setFullName(fullname);
        user.setPhone(phone);
        user.setRoleid(5);
        user.setCreatedDate(new Date());
        user.setActive(false);

        userService.register(user);

        boolean sent = userService.sendOTPForRegistration(email, username);

        if (sent) {
            HttpSession session = req.getSession();
            session.setAttribute("registerUsername", username);
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } else {
            alertMsg = "Loi khi gui email! Vui long thu lai.";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
        }
    }
}
