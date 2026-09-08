<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h3>Nội dung của admin</h3>
<ul>
    <li>
        <a href="${pageContext.request.contextPath}/admin/category">
            Quản lý Danh mục (/admin/category)
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/product">
            Quản lý Sản phẩm (/admin/product)
        </a>
    </li>
</ul>