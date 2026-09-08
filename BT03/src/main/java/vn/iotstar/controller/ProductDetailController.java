package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Product;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.impl.ProductServiceImpl;

@WebServlet(urlPatterns = { "/product/detail" })
public class ProductDetailController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/product");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam.trim());
            Product product = productService.findById(productId);
            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/product");
                return;
            }

            req.setAttribute("product", product);
            req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/product");
        }
    }
}
