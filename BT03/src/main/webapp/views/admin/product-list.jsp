<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="page-header">
    <div class="pull-left">
        <h2>Quản lý sản phẩm</h2>
    </div>
    <div class="pull-right">
        <a href="${pageContext.request.contextPath}/admin/products/add${not empty categoryId and categoryId > 0 ? '?categoryId='.concat(categoryId) : ''}" class="btn btn-success">Thêm sản phẩm</a>
    </div>
    <div class="clearfix"></div>
</div>

<div class="row" style="margin-bottom: 15px;">
    <div class="col-md-6">
        <form action="${pageContext.request.contextPath}/admin/products" method="get" class="form-inline">
            <label for="categoryId" style="margin-right: 10px;">Lọc theo danh mục:</label>
            <select name="categoryId" id="categoryId" class="form-control" onchange="this.form.submit()">
                <option value="0" ${categoryId == 0 ? 'selected' : ''}>-- Tất cả danh mục --</option>
                <c:forEach items="${categories}" var="c">
                    <option value="${c.categoryid}" ${categoryId == c.categoryid ? 'selected' : ''}>${c.categoryname}</option>
                </c:forEach>
            </select>
        </form>
    </div>
</div>

<div class="table-responsive">
    <table class="table table-bordered table-striped table-hover">
        <thead>
            <tr>
                <th style="width: 60px;">STT</th>
                <th style="width: 110px;">Hình ảnh</th>
                <th>Tên sản phẩm</th>
                <th>Danh mục</th>
                <th style="width: 120px;">Giá</th>
                <th style="width: 170px;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${productList}" var="p" varStatus="loop">
                <tr>
                    <td>${(page != null ? (page - 1) * pageSize : 0) + loop.index + 1}</td>
                    <td>
                        <c:if test="${not empty p.image}">
                            <img src="${pageContext.request.contextPath}/image?fname=${p.image}"
                                 alt="${p.productName}"
                                 style="width: 90px; height: 80px; object-fit: cover; border-radius: 4px; border: 1px solid #ddd;" />
                        </c:if>
                    </td>
                    <td style="text-align: left;">
                        <a href="${pageContext.request.contextPath}/admin/products/detail?id=${p.productId}&categoryId=${categoryId}"><b>${p.productName}</b></a>
                    </td>
                    <td>${p.category.categoryname}</td>
                    <td style="color: #d9534f; font-weight: bold;">${p.price} VNĐ</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/products/edit?id=${p.productId}&categoryId=${categoryId}" class="btn btn-warning btn-xs">Sửa</a>
                        <a href="${pageContext.request.contextPath}/admin/products/delete?id=${p.productId}&categoryId=${categoryId}" class="btn btn-danger btn-xs" onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${empty productList}">
    <div class="alert alert-info">Chưa có sản phẩm nào.</div>
</c:if>

<c:if test="${totalPages > 1}">
    <div style="margin-top: 15px; text-align: center;">
        <c:if test="${page > 1}">
            <a href="${pageContext.request.contextPath}/admin/products?categoryId=${categoryId}&page=${page - 1}" class="btn btn-default btn-sm">Trang trước</a>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == page}">
                    <span class="btn btn-primary btn-sm disabled"><b>${i}</b></span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/products?categoryId=${categoryId}&page=${i}" class="btn btn-default btn-sm">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${page < totalPages}">
            <a href="${pageContext.request.contextPath}/admin/products?categoryId=${categoryId}&page=${page + 1}" class="btn btn-default btn-sm">Trang sau</a>
        </c:if>
    </div>
</c:if>
