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

@WebServlet(urlPatterns = { "/verify-otp" })
public class VerifyOTPController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/views/verify-otp.jsp")
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
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String otp = req.getParameter("otp");

		otp = otp != null ? otp.trim() : "";

		req.setAttribute("otp", otp);

		if (otp.isEmpty()) {
			req.setAttribute("otpError", "Vui lòng nhập mã OTP");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		if (!otp.matches("^[0-9]{6}$")) {
			req.setAttribute("otpError", "Mã OTP phải gồm đúng 6 chữ số");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		String verifyMode = (String) session.getAttribute("verifyMode");

		if ("profile".equals(verifyMode)) {
			verifyProfile(req, resp, session, otp);
			return;
		}

		verifyRegister(req, resp, session, otp);
	}

	private void verifyRegister(
			HttpServletRequest req,
			HttpServletResponse resp,
			HttpSession session,
			String otp)
			throws ServletException, IOException {

		String email = (String) session.getAttribute("registerEmail");

		if (email == null) {
			resp.sendRedirect(req.getContextPath() + "/register");
			return;
		}

		User user = service.findByEmail(email);

		if (user == null) {
			req.setAttribute("otpError", "Không tìm thấy tài khoản");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		if (user.getStatus() == 1) {
			session.removeAttribute("registerEmail");
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (!otp.equals(user.getCode())) {
			req.setAttribute("otpError", "Mã OTP không chính xác");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		user.setStatus(1);
		user.setCode(null);

		service.update(user);

		session.removeAttribute("registerEmail");

		req.setAttribute("alert", "Kích hoạt tài khoản thành công. Vui lòng đăng nhập");

		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	private void verifyProfile(
			HttpServletRequest req,
			HttpServletResponse resp,
			HttpSession session,
			String otp)
			throws ServletException, IOException {

		Integer userId = (Integer) session.getAttribute("profileUserId");

		String sessionOtp = (String) session.getAttribute("profileOtp");

		if (userId == null || sessionOtp == null) {
			resp.sendRedirect(req.getContextPath() + "/member/myaccount");
			return;
		}

		if (!otp.equals(sessionOtp)) {
			req.setAttribute("otpError", "Mã OTP không chính xác");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		User user = service.findById(userId);

		if (user == null || user.getUserid() != userId) {
			req.setAttribute("otpError", "Không tìm thấy tài khoản");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		user.setUsername((String) session.getAttribute("profileUsername"));
		user.setFullname((String) session.getAttribute("profileFullname"));
		user.setEmail((String) session.getAttribute("profileEmail"));
		user.setPhone((String) session.getAttribute("profilePhone"));
		user.setAvatar((String) session.getAttribute("profileAvatar"));

		String password = (String) session.getAttribute("profilePassword");

		if (password != null && !password.isEmpty()) {
			user.setPassword(password);
		}

		service.update(user);

		session.setAttribute("account", user);

		session.removeAttribute("profileUsername");
		session.removeAttribute("profileFullname");
		session.removeAttribute("profilePassword");
		session.removeAttribute("profileEmail");
		session.removeAttribute("profilePhone");
		session.removeAttribute("profileAvatar");
		session.removeAttribute("profileOtp");
		session.removeAttribute("profileUserId");
		session.removeAttribute("verifyMode");

		req.setAttribute("alert", "Cập nhật thông tin thành công");
		req.setAttribute("user", user);

		req.getRequestDispatcher("/views/member/myaccount.jsp").forward(req, resp);
	}
}
