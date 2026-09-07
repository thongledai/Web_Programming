<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <h2>HTML Forms</h2>

  <form action="${pageContext.request.contextPath}/admin/category/insert" method="post">

    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname"><br>

    <label for="images">Images:</label><br>
    <input type="file" id="images" name="images"><br><br>

    <label for="status">Status:</label><br>
    <input type="text" id="status" name="status"><br><br>

    <input type="submit" value="Submit">
  </form>
  <form action="${pageContext.request.contextPath}/admin/category/insert" method="post" enctype="multipart/form-data">