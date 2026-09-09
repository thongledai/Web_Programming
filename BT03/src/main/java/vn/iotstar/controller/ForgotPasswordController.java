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
import vn.iotstar.utils.EmailUtils;

@WebServlet(urlPatterns = { "/forgetpassword" })
public class ForgotPasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/views/forgot-password.jsp")
				.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String email = req.getParameter("email");
		email = email != null ? email.trim() : "";

		req.setAttribute("email", email);

		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

		if (email.isEmpty()) {
			req.setAttribute("emailError", "Vui lòng nhập email");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (!email.matches(emailRegex)) {
			req.setAttribute("emailError", "Email không đúng định dạng");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		User user = service.findByEmail(email);

		if (user == null) {
			req.setAttribute("emailError", "Email không tồn tại trong hệ thống");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		String otp = generateOTP();

		user.setCode(otp);

		service.update(user);

		try {

			EmailUtils.sendOTP(email, otp);

			HttpSession session = req.getSession(true);

			session.setAttribute("forgotPasswordEmail", email);

			resp.sendRedirect(req.getContextPath() + "/reset-password");

		} catch (Exception e) {

			e.printStackTrace();

			req.setAttribute(
					"error",
					"Không thể gửi OTP đến email");

			req.getRequestDispatcher("/views/forgot-password.jsp")
					.forward(req, resp);
		}
	}

	private String generateOTP() {
		return String.valueOf((int) (Math.random() * 900000) + 100000);
	}
}