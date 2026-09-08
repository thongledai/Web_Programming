<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row">
    <div class="col-md-8">
        <h2>Thêm sản phẩm mới</h2>

        <form action="${pageContext.request.contextPath}/admin/product/add"
              method="post"
              enctype="multipart/form-data"
              class="form-horizontal">

            <input type="hidden" name="categoryId" value="${param.categoryId}" />

            <div class="form-group">
                <label class="col-sm-3 control-label">Tên sản phẩm</label>
                <div class="col-sm-9">
                    <input type="text" name="productName" class="form-control" required />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Giá bán</label>
                <div class="col-sm-9">
                    <input type="number" step="any" name="price" class="form-control" required />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Hình ảnh</label>
                <div class="col-sm-9">
                    <input type="file" name="image" class="form-control" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Mô tả</label>
                <div class="col-sm-9">
                    <textarea name="description" rows="5" class="form-control"></textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary">Lưu sản phẩm</button>
                    <a href="${pageContext.request.contextPath}/admin/product?categoryId=${param.categoryId}" class="btn btn-default">Hủy</a>
                </div>
            </div>
        </form>
    </div>
</div>