package vn.iotstar.controller.admin.category;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@WebServlet("/admin/categories/insert")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class CategoryInsertController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String categoryname = req.getParameter("categoryname");
        String statusStr = req.getParameter("status");

        categoryname = (categoryname != null) ? categoryname.trim() : "";
        statusStr = (statusStr != null) ? statusStr.trim() : "";

        req.setAttribute("categoryname", categoryname);
        req.setAttribute("status", statusStr);

        boolean hasError = false;

        if (categoryname.isEmpty()) {
            req.setAttribute("categorynameError", "Tên danh mục không được để trống");
            hasError = true;
        } else if (categoryname.length() < 2 || categoryname.length() > 100) {
            req.setAttribute("categorynameError", "Tên danh mục phải từ 2 đến 100 ký tự");
            hasError = true;
        }

        int status = 1;
        try {
            status = Integer.parseInt(statusStr);
            if (status != 0 && status != 1) {
                req.setAttribute("statusError", "Trạng thái phải là 0 hoặc 1");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            req.setAttribute("statusError", "Trạng thái không hợp lệ");
            hasError = true;
        }

        if (hasError) {
            req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
            return;
        }

        Category category = new Category();
        category.setCategoryname(categoryname);
        category.setStatus(status);

        // Upload image 1 lần duy nhất
        String fname = CategoryImageSupport.uploadFile(req, "images");
        if (fname != null && !fname.isEmpty()) {
            category.setImages(fname);
        } else {
            // Nếu không chọn file, copy default.jpg từ sample sang images với tên duy nhất (realtime)
            category.setImages(CategoryImageSupport.copyDefaultImage());
        }

        categoryService.insert(category);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
