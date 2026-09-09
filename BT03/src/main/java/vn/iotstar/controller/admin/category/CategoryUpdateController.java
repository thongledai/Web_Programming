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

@WebServlet(urlPatterns = { "/admin/categories/update", "/admin/category/update" })
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class CategoryUpdateController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String categoryidStr = req.getParameter("categoryid");
        String categoryname = req.getParameter("categoryname");
        String statusStr = req.getParameter("status");

        categoryname = (categoryname != null) ? categoryname.trim() : "";
        statusStr = (statusStr != null) ? statusStr.trim() : "";

        int categoryid = 0;
        int status = 1;
        boolean hasError = false;

        try {
            categoryid = Integer.parseInt(categoryidStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }

        if (categoryname.isEmpty()) {
            req.setAttribute("categorynameError", "Tên danh mục không được để trống");
            hasError = true;
        } else if (categoryname.length() < 2 || categoryname.length() > 100) {
            req.setAttribute("categorynameError", "Tên danh mục phải từ 2 đến 100 ký tự");
            hasError = true;
        }

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

        // Lấy category cũ
        Category oldCategory = categoryService.findById(categoryid);

        if (hasError) {
            Category editCate = new Category();
            editCate.setCategoryid(categoryid);
            editCate.setCategoryname(categoryname);
            editCate.setStatus(status);
            editCate.setImages((oldCategory != null) ? oldCategory.getImages() : "Default.jpg");

            req.setAttribute("cate", editCate);
            req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
            return;
        }

        // Upload ảnh mới
        String images = CategoryImageSupport.uploadFile(req, "images");

        // Nếu không chọn ảnh mới thì giữ ảnh cũ, nếu chọn ảnh mới thì xóa ảnh cũ
        if (images == null || images.isEmpty()) {
            if (oldCategory != null) {
                images = oldCategory.getImages();
            } else {
                images = "Default.jpg";
            }
        } else {
            // Có upload ảnh mới -> xóa file ảnh cũ khỏi đĩa
            if (oldCategory != null && oldCategory.getImages() != null) {
                CategoryImageSupport.deleteImage(oldCategory.getImages());
            }
        }

        Category category = new Category();
        category.setCategoryid(categoryid);
        category.setCategoryname(categoryname);
        category.setImages(images);
        category.setStatus(status);

        categoryService.update(category);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
