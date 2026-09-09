<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row">
    <div class="col-md-12">
        <h2>Chi tiết sản phẩm</h2>
    </div>
</div>

<div class="row">
    <div class="col-md-4">
        <c:if test="${not empty product.image}">
            <img src="${pageContext.request.contextPath}/image?fname=${product.image}"
                 alt="${product.productName}"
                 style="width: 100%; max-width: 300px; height: 260px; object-fit: cover; border: 1px solid #ddd; border-radius: 6px; background: #f9f9f9;" />
        </c:if>
    </div>
    <div class="col-md-8">
        <p><b>Mã SP:</b> ${product.productId}</p>
        <p><b>Tên SP:</b> ${product.productName}</p>
        <p><b>Giá:</b> ${product.price} VNĐ</p>
        <p><b>Danh mục:</b> ${product.category.categoryname}</p>
        <p><b>Mô tả:</b> ${empty product.description ? 'Chưa có mô tả.' : product.description}</p>

        <c:choose>
            <c:when test="${not empty param.categoryId}">
                <a href="${pageContext.request.contextPath}/admin/products?categoryId=${param.categoryId}" class="btn btn-default">⬅ Quay lại</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-default">⬅ Về danh sách</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>