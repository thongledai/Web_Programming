<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<a href="<c:url value='/admin/category/add'/>">Add Category</a>

<table border="1" width="100%">
	<tr>
		<th>STT</th>
		<th>Image</th>
		<th>CategoryID</th>
		<th>CategoryName</th>
		<th>Status</th>
		<th>Action</th>
	</tr>

	<c:forEach items="${listcate}" var="cate" varStatus="STT">
		<tr>
			<td>${STT.index + 1}</td>

			<td>
				<%-- Nếu là ảnh trong folder --%> <c:if
					test="${not empty cate.images && !cate.images.startsWith('http')}">
					<c:url value="/image" var="imgUrl">
						<c:param name="fname" value="${cate.images}" />
					</c:url>

					<img height="150" width="200" src="${imgUrl}" />
				</c:if> <%-- Nếu là ảnh trên web --%> <c:if
					test="${not empty cate.images && cate.images.startsWith('http')}">
					<img height="150" width="200" src="${cate.images}" />
				</c:if>
			</td>

			<td>${cate.categoryid}</td>
			<td>${cate.categoryname}</td>
			<td><c:if test="${cate.status==1}">
					<span>Còn hàng</span>
				</c:if> <c:if test="${cate.status==0}">
					<span>Hết hàng</span>
				</c:if></td>
			<td><a
				href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>">
					Sửa </a> | <a
				href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>">
					Xóa </a></td>
		</tr>
	</c:forEach>
</table>

