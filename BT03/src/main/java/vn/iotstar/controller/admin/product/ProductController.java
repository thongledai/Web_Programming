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
        "/admin/product",
        "/admin/product/add",
        "/admin/product/detail",
        "/admin/product/edit",
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

        if ("/admin/product".equals(servletPath)) {
            showProductList(req, resp);

        } else if ("/admin/product/add".equals(servletPath)) {
            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);

        } else if ("/admin/product/detail".equals(servletPath)) {
            showProductDetail(req, resp);

        } else if ("/admin/product/edit".equals(servletPath)) {
            showProductEdit(req, resp);

        } else if ("/admin/product/delete".equals(servletPath)) {
            deleteProduct(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String servletPath = req.getServletPath();

        if ("/admin/product/add".equals(servletPath)) {
            addProduct(req, resp);

        } else if ("/admin/product/edit".equals(servletPath)) {
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
            resp.sendRedirect(req.getContextPath() + "/admin/product");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            Product product = productService.findById(productId);

            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/product");
                return;
            }

            req.setAttribute("product", product);

            req.getRequestDispatcher("/views/admin/product-detail.jsp")
                    .forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/product");
        }
    }

    private void showProductEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/product");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            Product product = productService.findById(productId);

            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/product");
                return;
            }

            List<Category> categories = categoryService.findAll();

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);

            req.getRequestDispatcher("/views/admin/product-edit.jsp")
                    .forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/product");
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String productName = req.getParameter("productName");
        String priceParam = req.getParameter("price");
        String description = req.getParameter("description");
        String categoryIdParam = req.getParameter("categoryId");

        if (productName == null || productName.trim().isEmpty()
                || priceParam == null || priceParam.trim().isEmpty()
                || categoryIdParam == null || categoryIdParam.trim().isEmpty()) {

            req.setAttribute("error", "Vui lòng nhập đầy đủ thông tin sản phẩm");

            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);

            req.getRequestDispatcher("/views/admin/product-add.jsp")
                    .forward(req, resp);

            return;
        }

        try {
            double price = Double.parseDouble(priceParam);
            int categoryId = Integer.parseInt(categoryIdParam);

            Category category = categoryService.findById(categoryId);

            if (category == null) {
                req.setAttribute("error", "Danh mục không tồn tại");

                List<Category> categories = categoryService.findAll();
                req.setAttribute("categories", categories);

                req.getRequestDispatcher("/views/admin/product-add.jsp")
                        .forward(req, resp);

                return;
            }

            Product product = new Product();

            product.setProductName(productName.trim());
            product.setPrice(price);
            product.setDescription(description);
            product.setCategory(category);

            Part imagePart = req.getPart("image");

            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = uploadFile(imagePart);

                if (fileName != null) {
                    product.setImage(fileName);
                }
            }

            productService.insert(product);

            resp.sendRedirect(
                    req.getContextPath()
                    + "/admin/product?categoryId="
                    + categoryId
            );

        } catch (NumberFormatException e) {

            req.setAttribute("error", "Giá hoặc danh mục không hợp lệ");

            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);

            req.getRequestDispatcher("/views/admin/product-add.jsp")
                    .forward(req, resp);
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String idParam = req.getParameter("productId");
        String productName = req.getParameter("productName");
        String priceParam = req.getParameter("price");
        String description = req.getParameter("description");
        String categoryIdParam = req.getParameter("categoryId");

        if (idParam == null || categoryIdParam == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/product");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);
            double price = Double.parseDouble(priceParam);
            int categoryId = Integer.parseInt(categoryIdParam);

            Product product = productService.findById(productId);

            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/product");
                return;
            }

            Category category = categoryService.findById(categoryId);

            if (category == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/product");
                return;
            }

            product.setProductName(productName.trim());
            product.setPrice(price);
            product.setDescription(description);
            product.setCategory(category);

            String oldImage = product.getImage();

            Part imagePart = req.getPart("image");

            if (imagePart != null && imagePart.getSize() > 0) {

                String newImage = uploadFile(imagePart);

                if (newImage != null) {
                    product.setImage(newImage);

                    if (oldImage != null && !oldImage.isEmpty()) {
                        deleteImage(oldImage);
                    }
                }
            }

            productService.update(product);

            resp.sendRedirect(
                    req.getContextPath()
                    + "/admin/product?categoryId="
                    + categoryId
            );

        } catch (NumberFormatException e) {

            req.setAttribute("error", "Thông tin sản phẩm không hợp lệ");

            Product product = productService.findById(
                    Integer.parseInt(idParam)
            );

            List<Category> categories = categoryService.findAll();

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);

            req.getRequestDispatcher("/views/admin/product-edit.jsp")
                    .forward(req, resp);
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/product");
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

        resp.sendRedirect(req.getContextPath() + "/admin/product");
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