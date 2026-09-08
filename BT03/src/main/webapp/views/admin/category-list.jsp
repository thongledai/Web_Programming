<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<a href="${pageContext.request.contextPath}/admin/category/add">Add Category</a>
	
<form action="${pageContext.request.contextPath}/admin/category/search" method="get" style="margin-top: 10px; margin-bottom: 10px;">
    <input type="text" name="keyword" placeholder="Search category">
    <input type="submit" value="Search">
</form>

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
			<td>${(page != null ? (page - 1) * pageSize : 0) + STT.index + 1}</td>

			<td>
				<c:choose>
					<c:when test="${not empty cate.images and cate.images.startsWith('https')}">
						<c:set var="imgUrl" value="${cate.images}" />
					</c:when>
					<c:when test="${not empty cate.images}">
						<c:url value="/image?fname=${cate.images}" var="imgUrl" />
					</c:when>
					<c:otherwise>
						<c:url value="/image?fname=Default.jpg" var="imgUrl" />
					</c:otherwise>
				</c:choose>
				<img height="100" width="120" src="${imgUrl}" style="object-fit: cover;" />
			</td>

			<td>${cate.categoryid}</td>
			<td>${cate.categoryname}</td>

			<td>
				<c:if test="${cate.status == 1}">
					<span>Open</span>
				</c:if>
				<c:if test="${cate.status == 0}">
					<span>Close</span>
				</c:if>
			</td>
			<td>
				<a href="<c:url value='/admin/product?categoryId=${cate.categoryid}'/>">Xem SP</a> |
				<a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>">Edit</a> |
				<a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>" onclick="return confirm('Bạn có chắc muốn xóa?');">Delete</a>
			</td>
		</tr>
	</c:forEach>
</table>

<c:if test="${totalPages > 1}">
	<div style="margin-top: 15px; text-align: center;">
		<c:if test="${page > 1}">
			<a href="${pageContext.request.contextPath}/admin/category?page=${page - 1}">Trang trước</a>
		</c:if>

		<c:forEach begin="1" end="${totalPages}" var="i">
			<c:choose>
				<c:when test="${i == page}">
					<strong style="margin: 0 5px; font-size: 16px;">[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/admin/category?page=${i}" style="margin: 0 5px;">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:if test="${page < totalPages}">
			<a href="${pageContext.request.contextPath}/admin/category?page=${page + 1}">Trang sau</a>
		</c:if>
	</div>
</c:if>

