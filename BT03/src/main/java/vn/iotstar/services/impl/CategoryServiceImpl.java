package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.models.Category;
import vn.iotstar.services.ICategoryService;

public class CategoryServiceImpl
        implements ICategoryService {

    private final ICategoryDao categoryDao =
            new CategoryDaoImpl();


    @Override
    public void insert(Category category) {

        categoryDao.insert(category);
    }


    @Override
    public void edit(Category newCategory) {

        Category oldCategory =
                categoryDao.get(
                        newCategory.getId()
                );

        if (oldCategory == null) {

            return;
        }

        oldCategory.setName(
                newCategory.getName()
        );

        if (newCategory.getIcon() != null
                && !newCategory
                .getIcon()
                .isBlank()) {

            oldCategory.setIcon(
                    newCategory.getIcon()
            );
        }


        categoryDao.edit(
                oldCategory
        );
    }


    @Override
    public void delete(int id) {

        categoryDao.delete(id);
    }


    @Override
    public Category get(int id) {

        return categoryDao.get(id);
    }


    @Override
    public Category get(String name) {

        return categoryDao.get(name);
    }


    @Override
    public List<Category> getAll() {

        return categoryDao.getAll();
    }


    @Override
    public List<Category> search(
            String keyword
    ) {

        return categoryDao.search(
                keyword
        );
    }
}