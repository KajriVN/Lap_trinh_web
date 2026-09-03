package vn.iotstar.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/verify-otp"})
public class VerifyOTPController extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("registerUsername");
        String otp = req.getParameter("otp");

        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        boolean verified = userService.verifyOTP(username, otp);

        if (verified) {
            session.removeAttribute("registerUsername");
            req.setAttribute("alert", "Kich hoat tai khoan thanh cong! Vui long dang nhap.");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("alert", "Ma OTP khong dung hoac da het han!");
            req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
        }
    }
}
