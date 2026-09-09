package vn.iotstar.controller.admin.product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = {
        "/admin/products",
        "/admin/product",
        "/admin/products/add",
        "/admin/product/add",
        "/admin/products/detail",
        "/admin/product/detail",
        "/admin/products/edit",
        "/admin/product/edit",
        "/admin/products/delete",
        "/admin/product/delete"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IProductService productService = new ProductServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();

    private static final int PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String servletPath = req.getServletPath();

        if (servletPath.endsWith("/add")) {
            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);

        } else if (servletPath.endsWith("/detail")) {
            showProductDetail(req, resp);

        } else if (servletPath.endsWith("/edit")) {
            showProductEdit(req, resp);

        } else if (servletPath.endsWith("/delete")) {
            deleteProduct(req, resp);

        } else {
            showProductList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String servletPath = req.getServletPath();

        if (servletPath.endsWith("/add")) {
            addProduct(req, resp);

        } else if (servletPath.endsWith("/edit")) {
            updateProduct(req, resp);
        }
    }

    private void showProductList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int categoryId = 0;
        String categoryIdParam = req.getParameter("categoryId");
        if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdParam.trim());
            } catch (NumberFormatException e) {
                categoryId = 0;
            }
        }

        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam.trim());
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        if (page < 1) {
            page = 1;
        }

        long totalProducts = (categoryId > 0) ? productService.countByCategoryId(categoryId) : productService.count();

        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        if (totalPages > 0 && page > totalPages) {
            page = totalPages;
        }

        List<Product> productList = (categoryId > 0)
                ? productService.findByCategoryId(categoryId, page, PAGE_SIZE)
                : productService.findAll(page, PAGE_SIZE);

        List<Category> categories = categoryService.findAll();

        req.setAttribute("categories", categories);
        req.setAttribute("productList", productList);
        req.setAttribute("categoryId", categoryId);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", PAGE_SIZE);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/views/admin/product-list.jsp")
                .forward(req, resp);
    }

    private void showProductDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            Product product = productService.findById(productId);

            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }

            req.setAttribute("product", product);

            req.getRequestDispatcher("/views/admin/product-detail.jsp")
                    .forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    private void showProductEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            Product product = productService.findById(productId);

            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }

            List<Category> categories = categoryService.findAll();

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);

            req.getRequestDispatcher("/views/admin/product-edit.jsp")
                    .forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String productName = req.getParameter("productName");
        String priceParam = req.getParameter("price");
        String description = req.getParameter("description");
        String categoryIdParam = req.getParameter("categoryId");

        productName = (productName != null) ? productName.trim() : "";
        priceParam = (priceParam != null) ? priceParam.trim() : "";
        description = (description != null) ? description.trim() : "";
        categoryIdParam = (categoryIdParam != null) ? categoryIdParam.trim() : "";

        req.setAttribute("productName", productName);
        req.setAttribute("price", priceParam);
        req.setAttribute("description", description);
        req.setAttribute("categoryId", categoryIdParam);

        boolean hasError = false;

        if (productName.isEmpty()) {
            req.setAttribute("productNameError", "Tên sản phẩm không được để trống");
            hasError = true;
        } else if (productName.length() < 2 || productName.length() > 200) {
            req.setAttribute("productNameError", "Tên sản phẩm phải từ 2 đến 200 ký tự");
            hasError = true;
        }

        double price = 0;
        if (priceParam.isEmpty()) {
            req.setAttribute("priceError", "Giá bán không được để trống");
            hasError = true;
        } else {
            try {
                price = Double.parseDouble(priceParam);
                if (price < 0) {
                    req.setAttribute("priceError", "Giá bán phải lớn hơn hoặc bằng 0");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("priceError", "Giá bán phải là số hợp lệ");
                hasError = true;
            }
        }

        int categoryId = 0;
        Category category = null;
        if (categoryIdParam.isEmpty()) {
            req.setAttribute("categoryIdError", "Vui lòng chọn danh mục");
            hasError = true;
        } else {
            try {
                categoryId = Integer.parseInt(categoryIdParam);
                category = categoryService.findById(categoryId);
                if (category == null) {
                    req.setAttribute("categoryIdError", "Danh mục không tồn tại");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("categoryIdError", "Danh mục không hợp lệ");
                hasError = true;
            }
        }

        Part imagePart = req.getPart("image");

        if (hasError) {
            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
            return;
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);

        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = uploadFile(imagePart);
            if (fileName != null) {
                product.setImage(fileName);
            }
        }

        productService.insert(product);

        resp.sendRedirect(
                req.getContextPath()
                + "/admin/products?categoryId="
                + categoryId
        );
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String idParam = req.getParameter("productId");
        String productName = req.getParameter("productName");
        String priceParam = req.getParameter("price");
        String description = req.getParameter("description");
        String categoryIdParam = req.getParameter("categoryId");

        idParam = (idParam != null) ? idParam.trim() : "";
        productName = (productName != null) ? productName.trim() : "";
        priceParam = (priceParam != null) ? priceParam.trim() : "";
        description = (description != null) ? description.trim() : "";
        categoryIdParam = (categoryIdParam != null) ? categoryIdParam.trim() : "";

        int productId = 0;
        try {
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        boolean hasError = false;

        if (productName.isEmpty()) {
            req.setAttribute("productNameError", "Tên sản phẩm không được để trống");
            hasError = true;
        } else if (productName.length() < 2 || productName.length() > 200) {
            req.setAttribute("productNameError", "Tên sản phẩm phải từ 2 đến 200 ký tự");
            hasError = true;
        }

        double price = 0;
        if (priceParam.isEmpty()) {
            req.setAttribute("priceError", "Giá bán không được để trống");
            hasError = true;
        } else {
            try {
                price = Double.parseDouble(priceParam);
                if (price < 0) {
                    req.setAttribute("priceError", "Giá bán phải lớn hơn hoặc bằng 0");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("priceError", "Giá bán phải là số hợp lệ");
                hasError = true;
            }
        }

        int categoryId = 0;
        Category category = null;
        if (categoryIdParam.isEmpty()) {
            req.setAttribute("categoryIdError", "Vui lòng chọn danh mục");
            hasError = true;
        } else {
            try {
                categoryId = Integer.parseInt(categoryIdParam);
                category = categoryService.findById(categoryId);
                if (category == null) {
                    req.setAttribute("categoryIdError", "Danh mục không tồn tại");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("categoryIdError", "Danh mục không hợp lệ");
                hasError = true;
            }
        }

        Part imagePart = req.getPart("image");

        if (hasError) {
            Product editProduct = new Product();
            editProduct.setProductId(productId);
            editProduct.setProductName(productName);
            editProduct.setPrice(price);
            editProduct.setDescription(description);
            editProduct.setImage(existingProduct.getImage());
            editProduct.setCategory(category != null ? category : existingProduct.getCategory());

            List<Category> categories = categoryService.findAll();
            req.setAttribute("product", editProduct);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
            return;
        }

        existingProduct.setProductName(productName);
        existingProduct.setPrice(price);
        existingProduct.setDescription(description);
        existingProduct.setCategory(category);

        String oldImage = existingProduct.getImage();

        if (imagePart != null && imagePart.getSize() > 0) {
            String newImage = uploadFile(imagePart);
            if (newImage != null) {
                existingProduct.setImage(newImage);
                if (oldImage != null && !oldImage.isEmpty()) {
                    deleteImage(oldImage);
                }
            }
        }

        productService.update(existingProduct);

        resp.sendRedirect(
                req.getContextPath()
                + "/admin/products?categoryId="
                + categoryId
        );
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            Product product = productService.findById(productId);

            if (product != null) {

                String image = product.getImage();

                productService.delete(productId);

                if (image != null && !image.isEmpty()) {
                    deleteImage(image);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private int getCategoryId(HttpServletRequest req) {

        String categoryIdParam = req.getParameter("categoryId");

        if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {

            try {
                return Integer.parseInt(categoryIdParam.trim());
            } catch (NumberFormatException e) {
            }
        }

        List<Category> categories = categoryService.findAll();

        if (categories != null && !categories.isEmpty()) {
            return categories.get(0).getCategoryid();
        }

        return 0;
    }

    private String uploadFile(Part part) throws IOException {

        if (part == null || part.getSize() == 0) {
            return null;
        }

        String originalFileName =
                Paths.get(part.getSubmittedFileName())
                        .getFileName()
                        .toString();

        if (originalFileName.isEmpty()) {
            return null;
        }

        String extension = "";

        int dotIndex = originalFileName.lastIndexOf('.');

        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex);
        }

        String fileName =
                System.currentTimeMillis() + "_" + originalFileName;

        File uploadDir = new File(Constant.DIR);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        part.write(new File(uploadDir, fileName).getAbsolutePath());

        return fileName;
    }

    private void deleteImage(String fileName) {

        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }

        File file = new File(Constant.DIR, fileName);

        if (file.exists()) {
            file.delete();
        }
    }
}