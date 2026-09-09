<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="row margin-bottom-40">
    <div class="col-md-12">
        <div class="clearfix margin-bottom-20">
            <h2 class="pull-left">Danh sách sản phẩm</h2>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-default pull-right">Về trang chủ</a>
        </div>

        <div class="row product-list">
            <c:forEach var="product" items="${productList}">
                <div class="col-md-4 col-sm-6 col-xs-12" style="margin-bottom: 30px;">
                    <div class="product-item">
                        <div class="pi-img-wrapper" style="height: 280px; overflow: hidden; background: #f7f7f7;">
                            <c:if test="${not empty product.image}">
                                <img src="${pageContext.request.contextPath}/image?fname=${product.image}"
                                     alt="${product.productName}"
                                     class="img-responsive"
                                     style="width: 100%; height: 280px; object-fit: cover; display: block;">
                            </c:if>
                        </div>
                        <h3>
                            <a href="${pageContext.request.contextPath}/product/detail?id=${product.productId}">${product.productName}</a>
                        </h3>
                        <div class="pi-price"><fmt:formatNumber value="${product.price}" pattern="#,##0"/> VNĐ</div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty productList}">
            <div class="alert alert-info">Hiện chưa có sản phẩm nào để hiển thị.</div>
        </c:if>

        <nav aria-label="Page navigation" style="text-align: center; margin-top: 20px;">
            <ul class="pagination">
                <c:if test="${page > 1}">
                    <li><a href="${pageContext.request.contextPath}/product?page=${page - 1}">«</a></li>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="${i == page ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/product?page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${page < totalPages}">
                    <li><a href="${pageContext.request.contextPath}/product?page=${page + 1}">»</a></li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>