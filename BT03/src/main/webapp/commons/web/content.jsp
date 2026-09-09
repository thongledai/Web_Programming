<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="/commons/web/slider.jsp" %>

<div class="row margin-bottom-40">
	<div class="col-md-10 sale-product">
		<h2>New Products</h2>
		<div class="owl-carousel owl-carousel5">
			<c:forEach var="product" items="${newProducts}">
				<div>
					<div class="product-item">
						<div class="pi-img-wrapper">
							<img src="${pageContext.request.contextPath}/image?fname=${product.image}" class="img-responsive" alt="${product.productName}" style="height: 260px; object-fit: cover; width: 100%;">

						</div>
						<h3>
							<a href="${pageContext.request.contextPath}/product/detail?id=${product.productId}">${product.productName}</a>
						</h3>
						<div class="pi-price">${product.price} VNĐ</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

<div class="row margin-bottom-40">
	<div class="col-md-12">
		<a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Xem tất cả sản phẩm</a>
	</div>
</div>