package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Product;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.impl.ProductServiceImpl;

@WebServlet(urlPatterns = { "/home", "/index" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> newProducts = productService.findTop10NewProducts();
		req.setAttribute("newProducts", newProducts);
		req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
	}
}
