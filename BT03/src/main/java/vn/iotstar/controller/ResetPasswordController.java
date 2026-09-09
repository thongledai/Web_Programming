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

		req.setAttribute("otp", otp);

		boolean hasError = false;

		if (otp.isEmpty()) {
			req.setAttribute("otpError", "Vui lòng nhập mã OTP");
			hasError = true;
		} else if (!otp.matches("^[0-9]{6}$")) {
			req.setAttribute("otpError", "Mã OTP phải gồm 6 chữ số");
			hasError = true;
		}

		if (password.isEmpty()) {
			req.setAttribute("passwordError", "Mật khẩu mới không được để trống");
			hasError = true;
		} else if (password.length() < 6) {
			req.setAttribute("passwordError", "Mật khẩu phải từ 6 ký tự trở lên");
			hasError = true;
		}

		if (confirmPassword.isEmpty()) {
			req.setAttribute("confirmPasswordError", "Vui lòng nhập lại mật khẩu mới");
			hasError = true;
		} else if (!password.isEmpty() && !password.equals(confirmPassword)) {
			req.setAttribute("confirmPasswordError", "Password và Confirm Password không giống nhau");
			hasError = true;
		}

		if (hasError) {
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			return;
		}

		User user = service.findByEmail(email);

		if (user == null) {
			session.removeAttribute("forgotPasswordEmail");
			req.setAttribute("otpError", "Không tìm thấy tài khoản");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (!otp.equals(user.getCode())) {
			req.setAttribute("otpError", "Mã OTP không chính xác");
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
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