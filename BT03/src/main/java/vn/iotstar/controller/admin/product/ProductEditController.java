package vn.iotstar.controller.admin.product;
import java.io.IOException;
import java.nio.file.Paths;

import vn.iotstar.entity.Product;
import java.io.File;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;
import vn.iotstar.services.IProductService;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
// @WebServlet(urlPatterns = { "/admin/product/edit", "/admin/product/update" })
public class ProductEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IProductService productService = new ProductServiceImpl();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String productIdParam = req.getParameter("id");
		if (productIdParam != null && !productIdParam.trim().isEmpty()) {
			try {
				int productId = Integer.parseInt(productIdParam.trim());
				Product product = productService.findById(productId);
				req.setAttribute("product", product);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("productName");
		String priceStr=req.getParameter("price");
		String description=req.getParameter("description");
		int productId = Integer.parseInt(req.getParameter("productId"));
		double price = 0.0;
		if (priceStr != null && !priceStr.trim().isEmpty()) {
			try {
				price = Double.parseDouble(priceStr.trim());
			} catch (NumberFormatException e) {
				price = 0.0;
			}
		}
		// 1. Lấy entity từ DB lên (đã chứa sẵn tên ảnh cũ trong product.getImage())
		Product product = productService.findById(productId);
		product.setProductName(name);
		product.setPrice(price);
		product.setDescription(description);

		// 2. Chỉ set ảnh mới NẾU người dùng có chọn file
		try {
		    Part part = req.getPart("image");
		    if (part != null && part.getSize() > 0) {
		        String uploadPath = Constant.DIR;
		        File uploadDir = new File(uploadPath);
		        if (!uploadDir.exists()) {
		            uploadDir.mkdir();
		        }

		        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		        int index = filename.lastIndexOf(".");
		        String ext = (index > 0) ? filename.substring(index + 1) : "png";
		        String fname = System.currentTimeMillis() + "." + ext;
		        
		        // Ghi file mới
		        part.write(uploadPath + File.separator + fname);
		        
		        // Cập nhật tên ảnh mới vào product
		        product.setImage(fname);
		    }
		    // Không có nhánh else -> giữ nguyên ảnh cũ đang có sẵn trong product
		} catch (Exception e) {
		    e.printStackTrace();
		}

		// 3. Cập nhật xuống Database
		productService.update(product);
		resp.sendRedirect(req.getContextPath() + "/admin/product?categoryId=" + req.getParameter("categoryId"));

}
}
