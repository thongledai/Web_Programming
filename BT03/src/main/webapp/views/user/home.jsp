<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<%@ include file="/commons/web/slider.jsp" %>

<div class="container">

    <h2>Sản phẩm mới nhất</h2>

    <div class="row">

        <c:forEach items="${newProducts}" var="product">

            <div class="col-md-3">

                <div class="product">

                    <img
                        src="${pageContext.request.contextPath}/image?fname=${product.image}"
                        alt="${product.name}"
                        class="img-responsive">

                    <h4>${product.name}</h4>

                    <p><fmt:formatNumber value="${product.price}" pattern="#,##0"/> VNĐ</p>

                    <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}">
                        Xem chi tiết
                    </a>

                </div>

            </div>

        </c:forEach>

    </div>

</div>

<%@ include file="/commons/web/brands.jsp" %>

<%@ include file="/commons/web/steps.jsp" %>