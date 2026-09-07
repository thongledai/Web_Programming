<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<form action="${pageContext.request.contextPath}/admin/category/update"
	method="post" enctype="multipart/form-data">
	<input type="hidden" name="categoryid" value="${category.categoryid}">
	<label for="categoryname">Category Name:</label><br> 
	<input type="text" id="categoryname" name="categoryname"
		value="${category.categoryname}"><br> 
		
	<label for="images">Images:</label><br>

	<c:if test="${not empty category.images}">
		<c:choose>
			<c:when test="${fn:startsWith(category.images, 'http')}">
				<c:url value="${category.images}" var="imgUrl"></c:url>
			</c:when>
			<c:otherwise>
				<c:url value="/image?fname=${category.images}" var="imgUrl"></c:url>
			</c:otherwise>
		</c:choose>
		<img height="150" width="200" src="${imgUrl}" /> 
	</c:if>
	
	<input type="file" id="images" name="images"><br> 
	
	<label for="status">Status:</label><br> 
	<input type="text" id="status" name="status" value="${category.status}"><br> <br> 
	<input type="submit" value="Submit">
</form>