package vn.iotstar.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.CategoryServiceJpa;
import vn.iotstar.service.impl.ProductServiceJpa;
import vn.iotstar.util.Constant;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(urlPatterns = {"/admin/products", "/admin/product/add", "/admin/product/edit", "/admin/product/delete"})
public class ProductController extends HttpServlet {

    private IProductService productService = new ProductServiceJpa();
    private ICategoryService categoryService = new CategoryServiceJpa();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (url.contains("products")) {
            List<Product> list = productService.findAll();
            req.setAttribute("listproduct", list);
            req.getRequestDispatcher("/views/admin/list-product.jsp").forward(req, resp);
        } else if (url.contains("add")) {
            List<Category> listcate = categoryService.findAll();
            req.setAttribute("listcate", listcate);
            req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
        } else if (url.contains("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            List<Category> listcate = categoryService.findAll();
            req.setAttribute("product", product);
            req.setAttribute("listcate", listcate);
            req.getRequestDispatcher("/views/admin/edit-product.jsp").forward(req, resp);
        } else if (url.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                productService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (url.contains("add")) {
            String productName = req.getParameter("productname");
            String description = req.getParameter("description");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            int categoryId = Integer.parseInt(req.getParameter("categoryid"));
            int status = Integer.parseInt(req.getParameter("status"));

            Category category = categoryService.findById(categoryId);

            Product product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStatus(status);
            product.setCategory(category);
            product.setCreatedDate(new Date());

            String fname = "";
            String uploadPath = Constant.UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                Part part = req.getPart("image");
                if (part.getSize() > 0) {
                    String filename = part.getSubmittedFileName();
                    int index = filename.lastIndexOf(".");
                    String ext = filename.substring(index + 1);
                    fname = System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + "/" + fname);
                    product.setImage(fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            productService.insert(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");

        } else if (url.contains("edit")) {
            int productId = Integer.parseInt(req.getParameter("productid"));
            String productName = req.getParameter("productname");
            String description = req.getParameter("description");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            int categoryId = Integer.parseInt(req.getParameter("categoryid"));
            int status = Integer.parseInt(req.getParameter("status"));

            Product product = productService.findById(productId);
            String oldImage = product.getImage();

            Category category = categoryService.findById(categoryId);
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStatus(status);
            product.setCategory(category);

            String fname = "";
            String uploadPath = Constant.UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                Part part = req.getPart("image");
                if (part.getSize() > 0) {
                    String filename = part.getSubmittedFileName();
                    int index = filename.lastIndexOf(".");
                    String ext = filename.substring(index + 1);
                    fname = System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + "/" + fname);
                    product.setImage(fname);
                } else {
                    product.setImage(oldImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            productService.update(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }
}
