package vn.iotstar.controller.admin.category;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import vn.iotstar.utils.Constant;

/**
 * Helper dùng chung cho các Controller trong package category (Insert/Update/Delete)
 * để tránh lặp code upload/xóa ảnh. Đây KHÔNG phải là Servlet.
 */
final class CategoryImageSupport {

    private CategoryImageSupport() {
    }

    /**
     * Upload file từ request (nếu có) và trả về tên file mới đã lưu trên đĩa.
     * Trả về null nếu không có file nào được chọn hoặc có lỗi khi upload.
     */
    static String uploadFile(HttpServletRequest req, String paramName) {

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

            int index = originalFileName.lastIndexOf(".");

            String fileName;
            String extension = "";

            if (index > 0) {
                fileName = originalFileName.substring(0, index);
                extension = originalFileName.substring(index);
            } else {
                fileName = originalFileName;
            }

            String images = fileName + "." + System.currentTimeMillis() + extension;

            File uploadDir = new File(Constant.DIR);

            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                System.out.println("Tao thu muc upload: " + created);
            }

            part.write(Constant.DIR + File.separator + images);

            System.out.println("Upload thanh cong: " + images);

            return images;

        } catch (Exception e) {
            System.out.println("UPLOAD FILE ERROR:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Xóa file ảnh cũ khỏi thư mục upload (không xóa ảnh nằm trong thư mục sample
     * và không xóa các đường dẫn dạng URL bên ngoài).
     */
    static void deleteImage(String images) {

        if (images == null || images.trim().isEmpty() || images.startsWith("http")) {
            return;
        }

        try {
            String fileName = Paths.get(images).getFileName().toString();

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

    /**
     * Sao chép ảnh mặc định (Default.jpg) từ thư mục sample sang thư mục upload
     * với tên duy nhất (realtime) và trả về tên file đã tạo. Dùng khi thêm mới
     * category mà người dùng không chọn ảnh.
     */
    static String copyDefaultImage() {

        String defaultFilename = "Default." + System.currentTimeMillis() + ".jpg";
        File sampleFile = new File(Constant.SAMPLE_DIR, "Default.jpg");
        File destFile = new File(Constant.DIR, defaultFilename);

        if (sampleFile.exists()) {

            File uploadDir = new File(Constant.DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                Files.copy(sampleFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return defaultFilename;
            } catch (Exception e) {
                e.printStackTrace();
                return "Default.jpg";
            }
        }

        return "Default.jpg";
    }
}
