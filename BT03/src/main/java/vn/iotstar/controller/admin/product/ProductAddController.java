package vn.iotstar.controller.admin.product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IProductService;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
// @WebServlet("/admin/product/add")
public class ProductAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ICategoryService categoryService = new CategoryServiceImpl();
		IProductService productService = new ProductServiceImpl();
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String name=req.getParameter("productName");
		String priceStr = req.getParameter("price");
		double price = 0.0; // Giá trị mặc định

		if (priceStr != null && !priceStr.trim().isEmpty()) {
		    try {
		        price = Double.parseDouble(priceStr.trim());
		    } catch (NumberFormatException e) {
		        price = 0.0; 
		    }
		}
		String description=req.getParameter("description");
		Product product = new Product();
		product.setProductName(name);
		product.setPrice(price);
		product.setDescription(description);
		String fname = "";
		String uploadPath = Constant.DIR; // upload vào thư mục bất kỳ
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		try {
			Part part = req.getPart("image");
			if (part != null && part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				int index = filename.lastIndexOf(".");
				String ext = filename.substring(index + 1);
				fname = System.currentTimeMillis() + "." + ext;
				part.write(uploadPath + "/" + fname);
				product.setImage(fname);
			} else {
				product.setImage("avatar.png");
			}
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		}
		Category category = categoryService.findById(Integer.parseInt(req.getParameter("categoryId")));
		product.setCategory(category);
		productService.insert(product);

		// đưa model vào phương thức insert
		
		// chuyển trang
		String categoryId = req.getParameter("categoryId");
		if (categoryId == null || categoryId.trim().isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/admin/product");
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/admin/product?categoryId=" + categoryId);
	}
}
