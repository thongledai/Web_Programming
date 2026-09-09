package vn.iotstar.controller.admin.product;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.services.IProductService;


// @WebServlet("/admin/products/delete")
public class ProductDeleteController extends HttpServlet {
	IProductService productService = new ProductServiceImpl();
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("id");
		String categoryId = req.getParameter("categoryId");
		if (idParam != null && !idParam.trim().isEmpty()) {
			try {
				productService.delete(Integer.parseInt(idParam.trim()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (categoryId == null || categoryId.trim().isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/admin/products");
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/admin/products?categoryId=" + categoryId);
	}

}
