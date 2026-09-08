<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">

	<div class="content-form-page">

		<div class="row">

			<div class="col-md-7 col-sm-7">

				<h3>Forget Password</h3>

				<c:if test="${error != null}">
					<span style="color: red; display: block; margin-bottom: 15px;">
						${error}
					</span>
				</c:if>

				<form action="${pageContext.request.contextPath}/forgetpassword"
					method="post"
					class="form-horizontal form-without-legend"
					role="form">

					<div class="form-group">

						<label for="email" class="col-lg-4 control-label">
							Email <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="email"
								class="form-control"
								id="email"
								name="email"
								required
								style="color: black;">

						</div>

					</div>

					<div class="row">

						<div class="col-lg-8 col-md-offset-4 padding-left-0">

							<button type="submit"
								class="btn btn-primary">
								Send OTP
							</button>

						</div>

					</div>

				</form>

			</div>

		</div>

	</div>

</div>