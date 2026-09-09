package vn.iotstar.controller.admin.category;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@WebServlet("/admin/categories/delete")
public class CategoryDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));

        Category category = categoryService.findById(id);

        if (category != null) {

            String images = category.getImages();

            try {
                categoryService.delete(id);
            } catch (Exception e) {
                throw new ServletException("Không thể xóa category có id = " + id, e);
            }

            CategoryImageSupport.deleteImage(images);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
