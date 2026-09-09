<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>
        <sitemesh:write property="title"/>
    </title>

    <sitemesh:write property="head"/>
</head>

<body>

    <%@ include file="/commons/admin/header.jsp" %>

    <main>
        <sitemesh:write property="body"/>
    </main>

    <%@ include file="/commons/admin/footer.jsp" %>

</body>

</html>