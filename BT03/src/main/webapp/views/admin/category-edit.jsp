<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data">

    <input type="text" name="categoryid" value="${cate.categoryid}" hidden="hidden">

    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}"><br>

    <label for="images">Link images:</label><br>
    <input type="text" id="images" name="images" value="${cate.images}"><br>

    <c:if test="${cate.images.substring(0, 5) == 'https'}">
        <c:url value="${cate.images}" var="imgUrl"></c:url>
    </c:if>
    
    <c:if test="${cate.images.substring(0, 5) != 'https'}">
        <c:url value="/image?fname=${cate.images}" var="imgUrl"></c:url>
    </c:if>

    <img height="150" width="200" src="${imgUrl}" /><br>

    <label for="images1">Upload images:</label><br>
    <input type="file" id="images1" name="images1"><br>

    <label>Status</label><br>
    <input type="radio" id="ston" name="status" value="1" ${cate.status == 1 ? 'checked' : ''}>
    <label for="ston">Hoạt động</label><br>

    <input type="radio" id="stoff" name="status" value="0" ${cate.status != 1 ? 'checked' : ''}>
    <label for="stoff">Khóa</label>
    <br><br>

    <input type="submit" value="Update">

</form>