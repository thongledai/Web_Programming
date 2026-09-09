package vn.iotstar.controller.admin.category;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/categories/search", "/admin/category/search" })
public class CategorySearchController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("keyword");

        List<Category> list;

        if (keyword == null || keyword.trim().isEmpty()) {
            list = categoryService.findAll();
        } else {
            list = categoryService.find(keyword);
        }

        req.setAttribute("listcate", list);

        req.getRequestDispatcher("/views/admin/category-list.jsp")
                .forward(req, resp);
    }
}
