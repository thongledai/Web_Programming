<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row">
    <div class="col-md-8">
        <h2>Chỉnh sửa sản phẩm</h2>

        <form action="${pageContext.request.contextPath}/admin/products/edit"
              method="post"
              enctype="multipart/form-data"
              class="form-horizontal">

            <input type="hidden" name="productId" value="${product.productId != null && product.productId > 0 ? product.productId : param.id}" />

            <div class="form-group">
                <label class="col-sm-3 control-label">Danh mục sản phẩm</label>
                <div class="col-sm-9">
                    <select name="categoryId" class="form-control">
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach items="${categories}" var="c">
                            <option value="${c.categoryid}" ${(product.category.categoryid == c.categoryid || param.categoryId == c.categoryid) ? 'selected' : ''}>
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
                    <input type="text" name="productName" value="${product.productName}" class="form-control" required />
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
                    <input type="number" step="any" name="price" value="${product.price}" class="form-control" required />
                    <c:if test="${priceError != null}">
                        <span style="color: red; display: block; margin-top: 5px;">
                            ${priceError}
                        </span>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Ảnh hiện tại</label>
                <div class="col-sm-9">
                    <c:if test="${not empty product.image}">
                        <img src="${pageContext.request.contextPath}/image?fname=${product.image}"
                             alt="${product.productName}"
                             style="width: 120px; height: 100px; object-fit: cover; border: 1px solid #ddd; border-radius: 4px; margin-bottom: 10px;" />
                    </c:if>
                    <input type="file" name="image" accept="image/*" class="form-control" />
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
                    <textarea name="description" rows="5" class="form-control">${product.description}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary">Cập nhật sản phẩm</button>
                    <a href="${pageContext.request.contextPath}/admin/products?categoryId=${param.categoryId}" class="btn btn-default">Hủy</a>
                </div>
            </div>
        </form>
    </div>
</div>