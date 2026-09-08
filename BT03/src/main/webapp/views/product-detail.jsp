<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row margin-bottom-40">
    <div class="col-md-5 col-sm-5">
        <c:if test="${not empty product.image}">
            <img src="${pageContext.request.contextPath}/image?fname=${product.image}"
                 alt="${product.productName}"
                 class="img-responsive"
                 style="width: 100%; height: 420px; object-fit: cover; border: 1px solid #ddd; border-radius: 8px; background: #f7f7f7;" />
        </c:if>
    </div>

    <div class="col-md-7 col-sm-7">
        <h2>${product.productName}</h2>

        <div class="price-availability-block clearfix">
            <div class="price">
                <strong style="font-size: 28px; color: #e84d1c;">${product.price} VNĐ</strong>
            </div>
        </div>

        <div class="product-page-options">
            <div class="pull-left">
                <label class="control-label">Danh mục:</label>
                <span>${product.category.categoryname}</span>
            </div>
        </div>

        <hr>
        <p>${empty product.description ? 'Sản phẩm chưa có mô tả chi tiết.' : product.description}</p>

        <div class="review-quantity">
            <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Quay lại</a>
        </div>
    </div>
</div>