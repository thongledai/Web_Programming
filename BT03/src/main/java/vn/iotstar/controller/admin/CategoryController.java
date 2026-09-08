package vn.iotstar.controller.admin;

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
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = {
"/admin/categories",
"/admin/category/add",
"/admin/category/insert",
"/admin/category/edit",
"/admin/category/update",
"/admin/category/delete",
"/admin/category/search"
})
@MultipartConfig(
fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5,
maxRequestSize = 1024 * 1024 * 10
)
public class CategoryController extends HttpServlet {


private static final long serialVersionUID = 1L;

public ICategoryService categoryService = new CategoryServiceImpl();

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    String url = req.getRequestURI();

    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    if (url.contains("categories")) {

        List<Category> list = categoryService.findAll();

        req.setAttribute("listcate", list);

        req.getRequestDispatcher("/views/admin/category-list.jsp")
                .forward(req, resp);

    } else if (url.contains("add")) {

        req.getRequestDispatcher("/views/admin/category-add.jsp")
                .forward(req, resp);

    } else if (url.contains("edit")) {

        int id = Integer.parseInt(req.getParameter("id"));

        Category category = categoryService.findById(id);

        req.setAttribute("cate", category);

        req.getRequestDispatcher("/views/admin/category-edit.jsp")
                .forward(req, resp);

    } else if (url.contains("delete")) {

        int id = Integer.parseInt(req.getParameter("id"));

        Category category = categoryService.findById(id);

        if (category != null) {

            String images = category.getImages();

            try {
                categoryService.delete(id);
            } catch (Exception e) {
                throw new ServletException("Không thể xóa category có id = " + id, e);
            }

            deleteImage(images);
        }

        resp.sendRedirect(
                req.getContextPath()
                + "/admin/categories"
        );
    } else if (url.contains("update")) {

        req.getRequestDispatcher("/views/admin/category-update.jsp")
                .forward(req, resp);

    } else if (url.contains("search")) {

        String keyword = req.getParameter("keyword");

        List<Category> list;

        if (keyword == null || keyword.trim().isEmpty()) {
            list = categoryService.findAll();
        } else {
            list = categoryService.find(keyword);
        }

        req.setAttribute("listcate", list);

        req.getRequestDispatcher("/views/admin/category-list.jsp")
                .forward(req, resp);
    }
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    String url = req.getRequestURI();

    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    // INSERT CATEGORY
    if (url.contains("insert")) {

        String categoryname = req.getParameter("categoryname");
        int status = Integer.parseInt(req.getParameter("status"));

        Category category = new Category();
        category.setCategoryname(categoryname);
        category.setStatus(status);

        // Upload image 1 lần duy nhất
        String fname = uploadFile(req, "images");
        if (fname != null && !fname.isEmpty()) {
            category.setImages(fname);
        } else {
            // Nếu không chọn file, copy default.jpg từ sample sang images với tên duy nhất (realtime)
            String defaultFilename = "Default." + System.currentTimeMillis() + ".jpg";
            File sampleFile = new File(Constant.SAMPLE_DIR, "Default.jpg");
            File destFile = new File(Constant.DIR, defaultFilename);
            if (sampleFile.exists()) {
                File uploadDir = new File(Constant.DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                try {
                    java.nio.file.Files.copy(sampleFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    category.setImages(defaultFilename);
                } catch (Exception e) {
                    e.printStackTrace();
                    category.setImages("Default.jpg");
                }
            } else {
                category.setImages("Default.jpg");
            }
        }

        categoryService.insert(category);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    // UPDATE CATEGORY
    else if (url.contains("update")) {

        int categoryid = Integer.parseInt(req.getParameter("categoryid"));
        String categoryname = req.getParameter("categoryname");
        int status = Integer.parseInt(req.getParameter("status"));

        // Lấy category cũ
        Category oldCategory = categoryService.findById(categoryid);

        // Upload ảnh mới
        String images = uploadFile(req, "images");

        // Nếu không chọn ảnh mới thì giữ ảnh cũ, nếu chọn ảnh mới thì xóa ảnh cũ
        if (images == null || images.isEmpty()) {
            if (oldCategory != null) {
                images = oldCategory.getImages();
            } else {
                images = "Default.jpg";
            }
        } else {
            // Có upload ảnh mới -> xóa file ảnh cũ khỏi đĩa
            if (oldCategory != null && oldCategory.getImages() != null) {
                deleteImage(oldCategory.getImages());
            }
        }

        Category category = new Category();
        category.setCategoryid(categoryid);
        category.setCategoryname(categoryname);
        category.setImages(images);
        category.setStatus(status);

        categoryService.update(category);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}

// UPLOAD FILE

private String uploadFile(HttpServletRequest req, String paramName) {

    try {

        Part part = req.getPart(paramName);

        if (part == null || part.getSize() == 0) {
            System.out.println("Khong co file duoc upload");
            return null;
        }

        String originalFileName =
                Paths.get(part.getSubmittedFileName())
                     .getFileName()
                     .toString();

        // Tách tên file và phần mở rộng
        int index = originalFileName.lastIndexOf(".");

        String fileName;
        String extension = "";

        if (index > 0) {
            fileName = originalFileName.substring(0, index);
            extension = originalFileName.substring(index);
        } else {
            fileName = originalFileName;
        }

        // Tạo tên mới
        String images =
                fileName + "." + System.currentTimeMillis() + extension;

        File uploadDir = new File(Constant.DIR);

        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();

            System.out.println("Tao thu muc upload: " + created);
        }

        System.out.println("Thu muc upload: " + Constant.DIR);
        System.out.println("Ten file moi: " + images);

        part.write(
                Constant.DIR
                        + File.separator
                        + images
        );

        System.out.println("Upload thanh cong");

        return images;

    } catch (Exception e) {

        System.out.println("UPLOAD FILE ERROR:");
        e.printStackTrace();

        return null;
    }
}
private void deleteImage(String images) {

    if (images == null || images.trim().isEmpty() || images.startsWith("http")) {
        return;
    }

    try {
        String fileName = Paths.get(images).getFileName().toString();

        // Bảo vệ không xóa các file gốc/mẫu trong thư mục sample
        File sampleFile = new File(Constant.SAMPLE_DIR, fileName);
        if (sampleFile.exists()) {
            System.out.println("File thuoc thu muc sample, khong xoa: " + fileName);
            return;
        }

        File imageFile = new File(Constant.DIR, fileName);

        if (imageFile.exists()) {
            boolean deleted = imageFile.delete();
            System.out.println("Xoa file anh cu " + images + ": " + deleted);
        } else {
            System.out.println("Khong tim thay file anh cu: " + imageFile.getAbsolutePath());
        }

    } catch (Exception e) {
        System.out.println("DELETE IMAGE ERROR:");
        e.printStackTrace();
    }
}



}
