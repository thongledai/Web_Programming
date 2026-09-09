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

		boolean hasError = false;

		// 1. Validation username
		if (username.isEmpty()) {
			req.setAttribute("usernameError", "Username không được để trống");
			hasError = true;
		} else if (username.length() < 3 || username.length() > 50) {
			req.setAttribute("usernameError", "Username phải từ 3 đến 50 ký tự");
			hasError = true;
		} else if (service.FindByUserName(username) != null) {
			req.setAttribute("usernameError", "Username đã tồn tại");
			hasError = true;
		}

		// 2. Validation password
		if (password.isEmpty()) {
			req.setAttribute("passwordError", "Password không được để trống");
			hasError = true;
		} else if (password.length() < 6) {
			req.setAttribute("passwordError", "Password phải từ 6 ký tự trở lên");
			hasError = true;
		}

		// 3. Validation confirmPassword
		if (confirmPassword.isEmpty()) {
			req.setAttribute("confirmPasswordError", "Vui lòng nhập lại Password");
			hasError = true;
		} else if (!password.isEmpty() && !password.equals(confirmPassword)) {
			req.setAttribute("confirmPasswordError", "Password và Confirm Password không giống nhau");
			hasError = true;
		}

		// 4. Validation fullname
		if (fullname.isEmpty()) {
			req.setAttribute("fullnameError", "Họ tên không được để trống");
			hasError = true;
		}

		// 5. Validation email
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		if (email.isEmpty()) {
			req.setAttribute("emailError", "Email không được để trống");
			hasError = true;
		} else if (!email.matches(emailRegex)) {
			req.setAttribute("emailError", "Email không đúng định dạng");
			hasError = true;
		} else if (service.existsByEmail(email)) {
			req.setAttribute("emailError", "Email đã được sử dụng");
			hasError = true;
		}

		// 6. Validation phone
		if (!phone.isEmpty() && !phone.matches("^0[0-9]{9,10}$")) {
			req.setAttribute("phoneError", "Số điện thoại không hợp lệ (phải từ 10-11 số và bắt đầu bằng 0)");
			hasError = true;
		}

		if (hasError) {
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