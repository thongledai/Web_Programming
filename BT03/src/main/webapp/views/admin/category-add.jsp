<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Add Category</h2>

<form action="${pageContext.request.contextPath}/admin/categories/insert"
      method="post"
      enctype="multipart/form-data">

    <label for="categoryname">Category name:</label><br>
    <input type="text"
           id="categoryname"
           name="categoryname"
           value="${categoryname}">
    <c:if test="${categorynameError != null}">
        <span style="color: red; display: block; margin-top: 5px;">
            ${categorynameError}
        </span>
    </c:if>
    <br><br>

    <label for="images">Image:</label><br>
    <img id="imgPreview" style="display:none; margin-bottom:10px;" width="150" height="120" alt="Xem trước ảnh"><br>
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
    <br><br>

<label for="status">Status:</label><br>

<select id="status" name="status">
    <option value="1" ${status == '1' ? 'selected' : ''}>Open</option>
    <option value="0" ${status == '0' ? 'selected' : ''}>Close</option>
</select>
<c:if test="${statusError != null}">
    <span style="color: red; display: block; margin-top: 5px;">
        ${statusError}
    </span>
</c:if>

<br><br>

    <input type="submit" value="Submit">

</form>

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