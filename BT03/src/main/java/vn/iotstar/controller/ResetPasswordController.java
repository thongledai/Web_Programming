package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/reset-password" })
public class ResetPasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/views/reset-password.jsp")
				.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		HttpSession session = req.getSession(false);

		if (session == null) {
			resp.sendRedirect(req.getContextPath() + "/forgetpassword");
			return;
		}

		String email = (String) session.getAttribute("forgotPasswordEmail");

		if (email == null) {
			resp.sendRedirect(req.getContextPath() + "/forgetpassword");
			return;
		}

		String otp = req.getParameter("otp");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");

		otp = otp != null ? otp.trim() : "";
		password = password != null ? password.trim() : "";
		confirmPassword = confirmPassword != null
				? confirmPassword.trim()
				: "";

		if (otp.isEmpty()
				|| password.isEmpty()
				|| confirmPassword.isEmpty()) {

			req.setAttribute(
					"error",
					"Vui lòng nhập đầy đủ thông tin");

			req.getRequestDispatcher("/views/reset-password.jsp")
					.forward(req, resp);

			return;
		}

		if (!password.equals(confirmPassword)) {

			req.setAttribute(
					"error",
					"Password và Confirm Password không giống nhau");

			req.getRequestDispatcher("/views/reset-password.jsp")
					.forward(req, resp);

			return;
		}

		User user = service.findByEmail(email);

		if (user == null) {

			session.removeAttribute("forgotPasswordEmail");

			req.setAttribute(
					"error",
					"Không tìm thấy tài khoản");

			req.getRequestDispatcher("/views/forgot-password.jsp")
					.forward(req, resp);

			return;
		}

		if (!otp.equals(user.getCode())) {

			req.setAttribute(
					"error",
					"Mã OTP không chính xác");

			req.getRequestDispatcher("/views/reset-password.jsp")
					.forward(req, resp);

			return;
		}

		user.setPassword(password);
		user.setCode(null);

		service.update(user);

		session.removeAttribute("forgotPasswordEmail");

		req.setAttribute(
				"alert",
				"Đổi mật khẩu thành công. Vui lòng đăng nhập");

		req.getRequestDispatcher("/views/login.jsp")
				.forward(req, resp);
	}
}