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

@WebServlet(urlPatterns = { "/product" })
public class ProductPublicController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();
    private static final int PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam.trim());
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Product> allProducts = productService.findAll(1, Integer.MAX_VALUE);
        int totalItems = allProducts == null ? 0 : allProducts.size();
        int totalPages = totalItems == 0 ? 1 : (int) Math.ceil(totalItems / (double) PAGE_SIZE);

        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        List<Product> productList = productService.findAll(page, PAGE_SIZE);

        req.setAttribute("productList", productList);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalItems", totalItems);
        req.getRequestDispatcher("/views/product.jsp").forward(req, resp);
    }
}
