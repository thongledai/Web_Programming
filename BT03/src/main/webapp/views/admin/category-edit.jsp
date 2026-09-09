<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form action="${pageContext.request.contextPath}/admin/categories/update"
      method="post"
      enctype="multipart/form-data">

    <input type="hidden"
           id="categoryid"
           name="categoryid"
           value="${cate.categoryid}">

    <label for="categoryname">Category Name:</label><br>

    <input type="text"
           id="categoryname"
           name="categoryname"
           value="${cate.categoryname}">
    <c:if test="${categorynameError != null}">
        <span style="color: red; display: block; margin-top: 5px;">
            ${categorynameError}
        </span>
    </c:if>

    <br>

    <label>Image:</label><br>
    <c:choose>
        <c:when test="${not empty cate.images and cate.images.startsWith('https')}">
            <c:set var="imgUrl" value="${cate.images}" />
            <img id="imgPreview" src="${imgUrl}" width="150" height="120" alt="Ảnh xem trước"><br>
        </c:when>
        <c:when test="${not empty cate.images}">
            <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
            <img id="imgPreview" src="${imgUrl}" width="150" height="120" alt="Ảnh xem trước"><br>
        </c:when>
        <c:otherwise>
            <img id="imgPreview" style="display:none;" width="150" height="120" alt="Ảnh xem trước"><br>
        </c:otherwise>
    </c:choose>

    <label for="images">New Image:</label><br>
    <input type="file"
           id="images"
           name="images"
           accept="image/*"
           onchange="previewImage(this)">
    <c:if test="${imagesError != null}">
        <span style="color: red; display: block; margin-top: 5px;">
            ${imagesError}
        </span>
    </c:if>

    <script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var img = document.getElementById('imgPreview');
                if (img) {
                    img.src = e.target.result;
                    img.style.display = 'block';
                }
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
    </script>

    <br><br>

<label for="status">Status:</label><br>

<select id="status" name="status">

    <option value="1" ${cate.status == 1 ? 'selected' : ''}>
        Open
    </option>

    <option value="0" ${cate.status == 0 ? 'selected' : ''}>
        Close
    </option>

</select>
<c:if test="${statusError != null}">
    <span style="color: red; display: block; margin-top: 5px;">
        ${statusError}
    </span>
</c:if>

<br><br>

    <input type="submit" value="Submit">

</form>