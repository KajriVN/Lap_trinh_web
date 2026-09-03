package vn.iotstar.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.ProductServiceJpa;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    private IProductService productService = new ProductServiceJpa();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> latestProducts = productService.findLatest(10);
        req.setAttribute("latestProducts", latestProducts);
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
