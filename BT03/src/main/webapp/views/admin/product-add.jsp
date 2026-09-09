<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row">
    <div class="col-md-8">
        <h2>Thêm sản phẩm mới</h2>

        <form action="${pageContext.request.contextPath}/admin/products/add"
              method="post"
              enctype="multipart/form-data"
              class="form-horizontal">

            <div class="form-group">
                <label class="col-sm-3 control-label">Danh mục sản phẩm</label>
                <div class="col-sm-9">
                    <select name="categoryId" class="form-control">
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach items="${categories}" var="c">
                            <option value="${c.categoryid}" ${(not empty categoryId ? categoryId : param.categoryId) == c.categoryid ? 'selected' : ''}>
                                ${c.categoryname}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${categoryIdError != null}">
                        <span style="color: red; display: block; margin-top: 5px;">
                            ${categoryIdError}
                        </span>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Tên sản phẩm</label>
                <div class="col-sm-9">
                    <input type="text" name="productName" value="${not empty productName ? productName : param.productName}" class="form-control" required />
                    <c:if test="${productNameError != null}">
                        <span style="color: red; display: block; margin-top: 5px;">
                            ${productNameError}
                        </span>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Giá bán</label>
                <div class="col-sm-9">
                    <input type="number" step="any" name="price" value="${not empty price ? price : param.price}" class="form-control" required />
                    <c:if test="${priceError != null}">
                        <span style="color: red; display: block; margin-top: 5px;">
                            ${priceError}
                        </span>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Hình ảnh</label>
                <div class="col-sm-9">
                    <input type="file" name="image" class="form-control" accept="image/*" />
                    <c:if test="${imageError != null}">
                        <span style="color: red; display: block; margin-top: 5px;">
                            ${imageError}
                        </span>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Mô tả</label>
                <div class="col-sm-9">
                    <textarea name="description" rows="5" class="form-control">${not empty description ? description : param.description}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary">Lưu sản phẩm</button>
                    <a href="${pageContext.request.contextPath}/admin/products?categoryId=${param.categoryId}" class="btn btn-default">Hủy</a>
                </div>
            </div>
        </form>
    </div>
</div>