package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(
        urlPatterns = {
                "/admin/category/edit"
        }
)
@MultipartConfig
public class CategoryEditController
        extends HttpServlet {

    private final ICategoryService cateService =
            new CategoryServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String id =
                req.getParameter("id");

        CategoryModel category =
                cateService.get(
                        Integer.parseInt(id)
                );

        req.setAttribute(
                "category",
                category
        );

        RequestDispatcher dispatcher =
                req.getRequestDispatcher(
                        "/views/admin/edit-category.jsp"
                );

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int id =
                Integer.parseInt(
                        req.getParameter("id")
                );

        String name =
                req.getParameter("name");

        Part iconPart =
                req.getPart("icon");

        String iconPath = null;

        if (iconPart != null
                && iconPart.getSize() > 0
                && iconPart.getSubmittedFileName() != null
                && !iconPart.getSubmittedFileName().isBlank()) {

            String originalName =
                    new File(
                            iconPart.getSubmittedFileName()
                    ).getName();

            String extension = "";

            int index =
                    originalName.lastIndexOf('.');

            if (index >= 0) {
                extension =
                        originalName.substring(index);
            }

            String fileName =
                    System.currentTimeMillis()
                            + extension;

            File uploadDir =
                    new File(
                            Constant.DIR,
                            "category"
                    );

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File file =
                    new File(
                            uploadDir,
                            fileName
                    );

            iconPart.write(
                    file.getAbsolutePath()
            );

            iconPath =
                    "category/" + fileName;
        }

        CategoryModel category =
                new CategoryModel();

        category.setId(id);
        category.setName(name);
        category.setIcon(iconPath);

        cateService.edit(category);

        resp.sendRedirect(
                req.getContextPath()
                        + "/admin/category/list"
        );
    }
}