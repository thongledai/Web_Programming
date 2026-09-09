<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">

	<div class="content-form-page">

		<div class="row">

			<div class="col-md-7 col-sm-7">

				<h3>Reset Password</h3>

				<c:if test="${error != null}">
					<span style="color: red; display: block; margin-bottom: 15px;">
						${error}
					</span>
				</c:if>

				<form action="${pageContext.request.contextPath}/reset-password"
					method="post"
					class="form-horizontal form-without-legend"
					role="form">

					<div class="form-group">

						<label for="otp" class="col-lg-4 control-label">
							OTP <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="text"
								class="form-control"
								id="otp"
								name="otp"
								value="${otp}"
								maxlength="6"
								required
								style="color: black;">

							<c:if test="${otpError != null}">
								<span style="color: red; display: block; margin-top: 5px;">
									${otpError}
								</span>
							</c:if>

						</div>

					</div>

					<div class="form-group">

						<label for="password" class="col-lg-4 control-label">
							New Password <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="password"
								class="form-control"
								id="password"
								name="password"
								required
								style="color: black;">

							<c:if test="${passwordError != null}">
								<span style="color: red; display: block; margin-top: 5px;">
									${passwordError}
								</span>
							</c:if>

						</div>

					</div>

					<div class="form-group">

						<label for="confirmPassword" class="col-lg-4 control-label">
							Confirm Password <span class="require"></span>
						</label>

						<div class="col-lg-8">

							<input type="password"
								class="form-control"
								id="confirmPassword"
								name="confirmPassword"
								required
								style="color: black;">

							<c:if test="${confirmPasswordError != null}">
								<span style="color: red; display: block; margin-top: 5px;">
									${confirmPasswordError}
								</span>
							</c:if>

						</div>

					</div>

					<div class="row">

						<div class="col-lg-8 col-md-offset-4 padding-left-0">

							<button type="submit"
								class="btn btn-primary">
								Reset Password
							</button>

						</div>

					</div>

				</form>

			</div>

		</div>

	</div>

</div>