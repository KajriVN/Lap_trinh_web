package vn.iotstar.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.ProductServiceJpa;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/product"})
public class ProductListController extends HttpServlet {

    private IProductService productService = new ProductServiceJpa();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageSize = 6;
        int page = 0;
        
        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        List<Product> list = productService.findAll(page, pageSize);
        
        int totalProducts = productService.count();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        req.setAttribute("listproduct", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/product-list.jsp").forward(req, resp);
    }
}
