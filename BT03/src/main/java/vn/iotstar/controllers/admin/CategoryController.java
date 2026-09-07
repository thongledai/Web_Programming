package vn.iotstar.controllers.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@MultipartConfig
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", 
		"/admin/category/insert", "/admin/category/edit", 
		"/admin/category/update", "/admin/category/delete", 
		"/admin/category/search" }

)
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public ICategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("categories")) {
			List<CategoryModel> list = categoryService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		} else if (url.contains("add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
		}else if (url.contains("edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			
			CategoryModel category = categoryService.findById(id);
			
			req.setAttribute("category", category);
			
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
		}else if (url.contains("delete")) {
			req.getRequestDispatcher("/views/admin/category-delete.jsp").forward(req, resp);
		}else if (url.contains("update")) {
			req.getRequestDispatcher("/views/admin/category-update.jsp").forward(req, resp);
		}else if (url.contains("search")) {
			req.getRequestDispatcher("/views/admin/category-search.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("insert")) {
			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));
			String images = "https://macone.vn/wp-content/uploads/2025/09/iPhone-17-Den-1.png";

			CategoryModel category = new CategoryModel();
			category.setCategoryname(categoryname);
			category.setImages(images);
			category.setStatus(status);

			categoryService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}
}
