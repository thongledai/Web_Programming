<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-12 col-sm-12">

	<div class="content-form-page">

		<div class="row">

			<div class="col-md-7 col-sm-7">

				<h3>Verify OTP</h3>

				<c:if test="${error != null}">
					<span style="color: red; display: block; margin-bottom: 15px;">
						${error}
					</span>
				</c:if>

				<p>
					Mã OTP đã được gửi đến email của bạn.
				</p>

				<form action="${pageContext.request.contextPath}/verify-otp"
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

					<div class="row">

						<div class="col-lg-8 col-md-offset-4 padding-left-0">

							<button type="submit"
								class="btn btn-primary">
								Verify
							</button>

						</div>

					</div>

				</form>

			</div>

		</div>

	</div>

</div>