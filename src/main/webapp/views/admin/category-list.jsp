<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý danh mục</title>
    <!-- Nhúng Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Nhúng FontAwesome để thêm icon cho nút bấm sinh động hơn -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

    <div class="container py-5">
        <!-- Header tiêu đề và nút thêm mới -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-dark mb-1">Quản lý Danh mục</h2>
                <p class="text-muted mb-0">Hiển thị và quản lý danh sách danh mục sản phẩm</p>
            </div>
            <a href="<c:url value='/admin/category/add'/>" class="btn btn-primary shadow-sm">
                <i class="fa-solid fa-plus me-1"></i> Thêm danh mục
            </a>
        </div>
        
        <!-- Bảng hiển thị dữ liệu được bọc trong một Card có bo góc và bóng mờ -->
        <div class="card shadow-sm border-0 rounded-4 overflow-hidden">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0 text-center">
                        <thead class="table-dark text-uppercase fs-7">
                            <tr>
                                <th class="py-3" style="width: 8%;">STT</th>
                                <th class="py-3" style="width: 20%;">Hình ảnh</th>
                                <th class="py-3 text-start ps-4" style="width: 32%;">Tên danh mục</th>
                                <th class="py-3" style="width: 15%;">Trạng thái</th>
                                <th class="py-3" style="width: 25%;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listcate}" var="cate" varStatus="STT">
                                <tr>
                                    <td class="fw-semibold text-secondary">${STT.index + 1}</td>
                                    
                                    <!-- LOGIC CŨ: Xử lý đường dẫn ảnh an toàn -->
                                    <c:choose>
                                        <c:when test="${not empty cate.images and cate.images.length() >= 5 and cate.images.substring(0, 5) == 'https'}">
                                            <c:url value="${cate.images}" var="imgUrl"></c:url>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url value="/image?fname=${cate.images}" var="imgUrl"></c:url>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <!-- LOGIC CŨ: Hiển thị ảnh với cơ chế chống lỗi -->
                                    <td class="py-3">
                                        <div class="bg-white p-1 border rounded shadow-xs d-inline-block">
                                            <img src="${imgUrl}" class="rounded" style="height: 65px; width: 85px; object-fit: cover;" 
                                                 onerror="this.src='https://via.placeholder.com/100x80?text=No+Image'" />
                                        </div>
                                    </td>
                                    
                                    <td class="fw-bold text-start ps-4 text-dark">${cate.categoryname}</td>
                                    
                                    <td>
                                        <c:if test="${cate.status == 1}">
                                            <span class="badge bg-success bg-opacity-10 text-success px-3 py-2 rounded-pill fw-semibold">Hoạt động</span>
                                        </c:if>
                                        <c:if test="${cate.status != 1}">
                                            <span class="badge bg-danger bg-opacity-10 text-danger px-3 py-2 rounded-pill fw-semibold">Khóa</span>
                                        </c:if>
                                    </td>
                                    
                                    <td>
                                        <a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>" class="btn btn-outline-warning btn-sm px-3 me-1">
                                            <i class="fa-solid fa-pen-to-square"></i> Sửa
                                        </a> 
                                        <a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>" class="btn btn-outline-danger btn-sm px-3" onclick="return confirm('Bạn có chắc muốn xóa danh mục này không?');">
                                            <i class="fa-solid fa-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</body>
</html>