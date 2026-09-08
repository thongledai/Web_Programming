package vn.iotstar.controller.admin.product;

import java.io.IOException;

import vn.iotstar.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.services.IProductService;


// @WebServlet(urlPatterns = {  "/admin/product/detail" })
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Product product = productService.findById(id);
                if (product != null) {
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("/views/admin/product-detail.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/product?categoryId=" + req.getParameter("categoryId"));
    }
}