<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h2>Add Category</h2>

<form action="${pageContext.request.contextPath}/admin/category/insert"
      method="post"
      enctype="multipart/form-data">

    <label for="categoryname">Category name:</label><br>
    <input type="text"
           id="categoryname"
           name="categoryname">
    <br><br>

    <label for="images">Image:</label><br>
    <img id="imgPreview" style="display:none; margin-bottom:10px;" width="150" height="120" alt="Xem trước ảnh"><br>
    <input type="file"
           id="images"
           name="images"
           accept="image/*"
           onchange="previewImage(this)">
    <br><br>

<label for="status">Status:</label><br>

<select id="status" name="status">
    <option value="1">Open</option>
    <option value="0">Close</option>
</select>

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