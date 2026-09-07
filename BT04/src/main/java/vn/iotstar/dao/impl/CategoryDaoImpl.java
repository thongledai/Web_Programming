package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.configs.DBConnection;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.models.CategoryModel;

public class CategoryDaoImpl implements ICategoryDao {
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public List<CategoryModel> find(String keyword) {

		String sql = "SELECT * FROM categories " + "WHERE CAST(categoryid AS VARCHAR) LIKE ? "
				+ "OR categoryname LIKE ? " + "OR CAST(status AS VARCHAR) LIKE ?";

		List<CategoryModel> list = new ArrayList<>();

		try {
			conn = new DBConnection().getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				CategoryModel category = new CategoryModel();

				category.setCategoryid(rs.getInt("categoryid"));
				category.setCategoryname(rs.getString("categoryname"));
				category.setImages(rs.getString("images"));
				category.setStatus(rs.getInt("status"));

				list.add(category);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public CategoryModel findById(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM categories WHERE id = ?";
		try {
			conn = new DBConnection().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				CategoryModel category = new CategoryModel();
				category.setCategoryid(rs.getInt("id"));
				category.setCategoryname(rs.getString("name"));
				category.setImages(rs.getString("images"));
				category.setStatus(rs.getInt("status"));
				return category;
			}
			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void update(CategoryModel category) {
		// TODO Auto-generated method stub
		String sql = "UPDATE categories SET categoryname=?, images=?, status=? WHERE categoryid=?";
		try {
			conn = new DBConnection().getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, category.getCategoryname());
			ps.setString(2, category.getImages());
			ps.setInt(3, category.getStatus());
			ps.setInt(4, category.getCategoryid());

			ps.executeUpdate();

			conn.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CategoryModel> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM categories";
		List<CategoryModel> list = new ArrayList<>();
		try {
			conn = new DBConnection().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				CategoryModel category = new CategoryModel();
				category.setCategoryid(rs.getInt("categoryid"));
				category.setCategoryname(rs.getString("categoryname"));
				category.setImages(rs.getString("images"));
				category.setStatus(rs.getInt("status"));
				list.add(category);
			}
			conn.close();
			ps.close();
			rs.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void insert(CategoryModel category) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO categories(categoryname, images, status) VALUES(?, ?, ?)";
		try {
			conn = new DBConnection().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, category.getCategoryname());
			ps.setString(2, category.getImages());
			ps.setInt(3, category.getStatus());
			ps.executeUpdate();

			conn.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM categories WHERE categoryid=?";
		try {
			conn = new DBConnection().getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();

			conn.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}