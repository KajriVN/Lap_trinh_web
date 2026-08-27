package vn.iotstar.controller.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.impl.CategoryServiceJpa;
import vn.iotstar.util.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Single servlet handling all Category admin URLs.
 * Follows the pattern described in require1.txt §5.
 */
@MultipartConfig()
@WebServlet(urlPatterns = {
        "/admin/categories",
        "/admin/category/add",
        "/admin/category/insert",
        "/admin/category/edit",
        "/admin/category/update",
        "/admin/category/delete"
})
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService cateService = new CategoryServiceJpa();

    // ─── GET ────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        if (url.contains("/admin/categories")) {
            // List all categories
            List<Category> list = cateService.findAll();
            req.setAttribute("listcate", list);
            req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/add")) {
            // Show add form
            req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/edit")) {
            // Show edit form
            int id = Integer.parseInt(req.getParameter("id"));
            Category category = cateService.findById(id);
            req.setAttribute("cate", category);
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/delete")) {
            // Delete and redirect
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                cateService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    // ─── POST ───────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        if (url.contains("/admin/category/insert")) {
            handleInsert(req, resp);
        } else if (url.contains("/admin/category/update")) {
            handleUpdate(req, resp);
        }
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private void handleInsert(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String categoryname = req.getParameter("categoryname");
        int    status       = Integer.parseInt(req.getParameter("status"));
        String imagesLink   = req.getParameter("images");

        Category category = new Category();
        category.setCategoryname(categoryname);
        category.setStatus(status);

        String uploadPath = Constant.DIR;
        ensureUploadDir(uploadPath);

        try {
            Part part = req.getPart("images1");
            category.setImages(resolveImage(part, imagesLink, null, uploadPath));
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        cateService.insert(category);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        int    categoryId   = Integer.parseInt(req.getParameter("categoryid"));
        String categoryname = req.getParameter("categoryname");
        int    status       = Integer.parseInt(req.getParameter("status"));
        String imagesLink   = req.getParameter("images");

        Category category = cateService.findById(categoryId);
        if (category == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }

        String fileOld    = category.getImages();
        String uploadPath = Constant.DIR;
        ensureUploadDir(uploadPath);

        category.setCategoryname(categoryname);
        category.setStatus(status);

        try {
            Part part = req.getPart("images1");
            // If a new file was uploaded, delete the old one first (if local)
            if (part != null && part.getSize() > 0) {
                deleteOldFileIfLocal(fileOld, uploadPath);
            }
            category.setImages(resolveImage(part, imagesLink, fileOld, uploadPath));
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        cateService.update(category);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    /**
     * Decide which image value to store:
     *  1. A freshly uploaded file → save it, return the generated filename
     *  2. A URL typed in the link field   → use that string
     *  3. Nothing new                     → keep the old filename
     */
    private String resolveImage(Part part, String imagesLink, String oldImages, String uploadPath)
            throws IOException {

        if (part != null && part.getSize() > 0) {
            String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String ext      = filename.substring(filename.lastIndexOf('.') + 1);
            String fname    = System.currentTimeMillis() + "." + ext;
            part.write(uploadPath + "/" + fname);
            return fname;
        } else if (imagesLink != null && !imagesLink.isBlank()) {
            return imagesLink;
        } else {
            return oldImages != null ? oldImages : "avatar.png";
        }
    }

    private void ensureUploadDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
    }

    private void deleteOldFileIfLocal(String filename, String uploadPath) {
        if (filename == null || filename.startsWith("http")) return;
        Path path = Paths.get(uploadPath + File.separator + filename);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Utility kept for backwards compat if called externally. */
    public static void deleteFile(String filePath) throws IOException {
        Files.delete(Paths.get(filePath));
    }
}
