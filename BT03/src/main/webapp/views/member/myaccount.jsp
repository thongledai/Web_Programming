<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row margin-bottom-40">
  <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
    <h1>Tài khoản của tôi</h1>
    <c:if test="${not empty alert}"><div class="alert alert-success"><c:out value="${alert}" /></div></c:if>
    <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}" /></div></c:if>

    <c:choose>
      <c:when test="${edit}">
        <form method="post" enctype="multipart/form-data" class="form-horizontal form-without-legend">
          <div class="form-group">
            <label class="col-sm-3 control-label">Avatar</label>
            <div class="col-sm-9">
              <c:if test="${not empty user.avatar}"><img src="${pageContext.request.contextPath}/image?fname=${user.avatar}" alt="Avatar" class="img-thumbnail" style="max-width: 120px; margin-bottom: 10px;"></c:if>
              <input type="file" name="avatar" accept="image/png,image/jpeg,image/gif,image/webp" class="form-control">
            </div>
          </div>
          <div class="form-group"><label class="col-sm-3 control-label">Username *</label><div class="col-sm-9"><input class="form-control" name="username" required value="<c:out value='${user.username}'/>"></div></div>
          <div class="form-group"><label class="col-sm-3 control-label">Họ tên</label><div class="col-sm-9"><input class="form-control" name="fullname" value="<c:out value='${user.fullname}'/>"></div></div>
          <div class="form-group"><label class="col-sm-3 control-label">Email *</label><div class="col-sm-9"><input type="email" class="form-control" name="email" required value="<c:out value='${user.email}'/>"></div></div>
          <div class="form-group"><label class="col-sm-3 control-label">Điện thoại</label><div class="col-sm-9"><input class="form-control" name="phone" value="<c:out value='${user.phone}'/>"></div></div>
          <div class="form-group"><label class="col-sm-3 control-label">Mật khẩu mới</label><div class="col-sm-9"><input type="password" class="form-control" name="password" placeholder="Để trống nếu không đổi"></div></div>
          <div class="form-group"><div class="col-sm-9 col-sm-offset-3"><button class="btn btn-primary" type="submit">Lưu thay đổi</button> <a class="btn btn-default" href="${pageContext.request.contextPath}/member/myaccount">Hủy</a></div></div>
          <p class="text-muted">Khi thay đổi email, số điện thoại hoặc mật khẩu, hệ thống sẽ gửi OTP đến email hiện tại để xác nhận.</p>
        </form>
      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col-sm-3"><c:choose><c:when test="${not empty user.avatar}"><img src="${pageContext.request.contextPath}/image?fname=${user.avatar}" alt="Avatar" class="img-responsive img-thumbnail"></c:when><c:otherwise><div class="well text-center"><i class="fa fa-user fa-4x"></i></div></c:otherwise></c:choose></div>
          <div class="col-sm-9"><dl class="dl-horizontal"><dt>Username</dt><dd><c:out value="${user.username}" /></dd><dt>Họ tên</dt><dd><c:out value="${user.fullname}" /></dd><dt>Email</dt><dd><c:out value="${user.email}" /></dd><dt>Điện thoại</dt><dd><c:out value="${user.phone}" /></dd></dl><a class="btn btn-primary" href="${pageContext.request.contextPath}/member/myaccount?edit=true"><i class="fa fa-pencil"></i> Chỉnh sửa thông tin</a></div>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
