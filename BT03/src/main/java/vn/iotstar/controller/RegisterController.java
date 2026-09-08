package vn.iotstar.controller;

import java.io.IOException;
import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.configs.JPAConfig;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.EmailUtils;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");

		username = username != null ? username.trim() : "";
		password = password != null ? password.trim() : "";
		confirmPassword = confirmPassword != null ? confirmPassword.trim() : "";
		fullname = fullname != null ? fullname.trim() : "";
		email = email != null ? email.trim() : "";
		phone = phone != null ? phone.trim() : "";

		req.setAttribute("username", username);
		req.setAttribute("fullname", fullname);
		req.setAttribute("email", email);
		req.setAttribute("phone", phone);

		if (username.isEmpty()
				|| password.isEmpty()
				|| confirmPassword.isEmpty()
				|| email.isEmpty()) {

			req.setAttribute(
					"passwordError",
					"Username, password và email không được để trống");

			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		if (!password.equals(confirmPassword)) {

			req.setAttribute(
					"passwordError",
					"Password và Confirm Password không giống nhau");

			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		if (service.FindByUserName(username) != null) {

			req.setAttribute(
					"passwordError",
					"Username đã tồn tại");

			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		if (service.existsByEmail(email)) {

			req.setAttribute(
					"passwordError",
					"Email đã được sử dụng");

			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		String otp = generateOTP();

		User user = new User();

		user.setUsername(username);
		user.setPassword(password);
		user.setFullname(fullname);
		user.setEmail(email);
		user.setPhone(phone);

		EntityManager em = JPAConfig.getEntityManager();

		try {
			Role role = em.getReference(Role.class, 3);
			user.setRole(role);
		} finally {
			em.close();
		}

		user.setCode(otp);
		user.setCreatedate(new Date());
		user.setStatus(0);
		user.setAvatar(null);
		user.setSellId(null);

		try {

		    EmailUtils.sendOTP(email, otp);

		    service.save(user);

		    HttpSession session = req.getSession(true);

		    session.setAttribute("registerEmail", email);

		    resp.sendRedirect(req.getContextPath() + "/verify-otp");

		} catch (Exception e) {

		    e.printStackTrace();

		    req.setAttribute(
		            "passwordError",
		            "Không thể gửi OTP. Tài khoản chưa được tạo");

		    req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
		}
	}

	private String generateOTP() {
		return String.valueOf((int) (Math.random() * 900000) + 100000);
	}
}