<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">

	<div class="content-form-page">

		<div class="row">

			<div class="col-md-7 col-sm-7">

				<c:if test="${alert != null}">
					<h3 class="alert">${alert}</h3>
				</c:if>

				<form action="${pageContext.request.contextPath}/register"
					method="post" class="form-horizontal form-without-legend"
					role="form">

					<div class="form-group">

						<label for="username" class="col-lg-4 control-label">
							Username <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="text" class="form-control"
								id="username" name="username"
								value="${username}" required
								style="color: black;">

						</div>

					</div>

					<div class="form-group">

						<label for="password" class="col-lg-4 control-label">
							Password <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="password" class="form-control"
								id="password" name="password"
								required style="color: black;">

						</div>

					</div>

					<div class="form-group">

						<label for="confirmPassword" class="col-lg-4 control-label">
							Confirm Password <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="password" class="form-control"
								id="confirmPassword" name="confirmPassword"
								required style="color: black;">

						</div>

					</div>

					<div class="form-group">

						<label for="fullname" class="col-lg-4 control-label">
							Fullname
						</label>

						<div class="col-lg-8">

							<input type="text" class="form-control"
								id="fullname" name="fullname"
								value="${fullname}"
								style="color: black;">

						</div>

					</div>

					<div class="form-group">

						<label for="email" class="col-lg-4 control-label">
							Email <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="email" class="form-control"
								id="email" name="email"
								value="${email}" required
								style="color: black;">

						</div>

					</div>

					<div class="form-group">

						<label for="phone" class="col-lg-4 control-label">
							Phone
						</label>

						<div class="col-lg-8">

							<input type="text" class="form-control"
								id="phone" name="phone"
								value="${phone}"
								style="color: black;">

						</div>

					</div>

					<c:if test="${passwordError != null}">
						<div class="form-group">

							<div class="col-lg-8 col-md-offset-4">

								<span style="color: red; display: block; margin-top: 5px;">
									${passwordError}
								</span>

							</div>

						</div>
					</c:if>

					<div class="row">

						<div class="col-lg-8 col-md-offset-4 padding-left-0">

							<a href="${pageContext.request.contextPath}/login">
								Already have an account? Login
							</a>

						</div>

					</div>

					<div class="row">

						<div class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-20">

							<button type="submit" class="btn btn-primary">
								Register
							</button>

						</div>

					</div>

				</form>

			</div>

		</div>

	</div>

</div>