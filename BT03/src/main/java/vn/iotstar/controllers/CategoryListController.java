package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryServiceImpl;

@WebServlet(
        urlPatterns = {
                "/admin/category/list"
        }
)
public class CategoryListController
        extends HttpServlet {

    private final ICategoryService cateService =
            new CategoryServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        List<Category> cateList =
                cateService.getAll();

        req.setAttribute(
                "cateList",
                cateList
        );

        RequestDispatcher dispatcher =
                req.getRequestDispatcher(
                        "/views/admin/list-category.jsp"
                );

        dispatcher.forward(req, resp);
    }
}