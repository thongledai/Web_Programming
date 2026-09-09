package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;
import vn.iotstar.utils.EmailUtils;

@WebServlet("/member/myaccount")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class MyAccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Set<String> IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
	private final IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User account = getAccount(req);
		if (account == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		User user = service.findById(account.getUserid());
		if (user == null) {
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		req.setAttribute("user", user);
		req.setAttribute("edit", "true".equals(req.getParameter("edit")));
		req.getRequestDispatcher("/views/member/myaccount.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		User account = getAccount(req);
		if (account == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		User user = service.findById(account.getUserid());
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String username = value(req, "username");
		String fullname = value(req, "fullname");
		String email = value(req, "email");
		String phone = value(req, "phone");
		String password = value(req, "password");

		User editUser = new User();
		editUser.setUserid(user.getUserid());
		editUser.setUsername(username);
		editUser.setFullname(fullname);
		editUser.setEmail(email);
		editUser.setPhone(phone);
		editUser.setAvatar(user.getAvatar());

		boolean hasError = false;

		// 1. Validation username
		if (username.isEmpty()) {
			req.setAttribute("usernameError", "Username không được để trống");
			hasError = true;
		} else if (username.length() < 3 || username.length() > 50) {
			req.setAttribute("usernameError", "Username phải từ 3 đến 50 ký tự");
			hasError = true;
		} else {
			User usernameOwner = service.FindByUserName(username);
			if (usernameOwner != null && usernameOwner.getUserid() != user.getUserid()) {
				req.setAttribute("usernameError", "Username đã được sử dụng");
				hasError = true;
			}
		}

		// 2. Validation fullname
		if (fullname.isEmpty()) {
			req.setAttribute("fullnameError", "Họ tên không được để trống");
			hasError = true;
		}

		// 3. Validation email
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		if (email.isEmpty()) {
			req.setAttribute("emailError", "Email không được để trống");
			hasError = true;
		} else if (!email.matches(emailRegex)) {
			req.setAttribute("emailError", "Email không đúng định dạng");
			hasError = true;
		} else {
			User emailOwner = service.findByEmail(email);
			if (emailOwner != null && emailOwner.getUserid() != user.getUserid()) {
				req.setAttribute("emailError", "Email đã được sử dụng");
				hasError = true;
			}
		}

		// 4. Validation phone
		if (!phone.isEmpty() && !phone.matches("^0[0-9]{9,10}$")) {
			req.setAttribute("phoneError", "Số điện thoại không hợp lệ (phải từ 10-11 số và bắt đầu bằng 0)");
			hasError = true;
		}

		// 5. Validation password
		if (!password.isEmpty() && password.length() < 6) {
			req.setAttribute("passwordError", "Mật khẩu mới phải từ 6 ký tự trở lên");
			hasError = true;
		}

		// 6. Avatar upload validation
		String avatar = null;
		try {
			avatar = uploadAvatar(req);
		} catch (ServletException e) {
			req.setAttribute("avatarError", e.getMessage());
			hasError = true;
		}

		if (hasError) {
			req.setAttribute("user", editUser);
			req.setAttribute("edit", true);
			req.getRequestDispatcher("/views/member/myaccount.jsp").forward(req, resp);
			return;
		}

		if (avatar == null) {
			avatar = user.getAvatar();
		}
		boolean verifyRequired = !email.equals(user.getEmail()) || !phone.equals(nullToEmpty(user.getPhone())) || !password.isEmpty();
		if (!verifyRequired) {
			user.setUsername(username);
			user.setFullname(fullname);
			user.setAvatar(avatar);
			service.update(user);
			req.getSession().setAttribute("account", user);
			req.setAttribute("alert", "Cập nhật thông tin thành công");
			req.setAttribute("user", user);
			req.getRequestDispatcher("/views/member/myaccount.jsp").forward(req, resp);
			return;
		}

		HttpSession session = req.getSession();
		session.setAttribute("verifyMode", "profile");
		session.setAttribute("profileUserId", user.getUserid());
		session.setAttribute("profileUsername", username);
		session.setAttribute("profileFullname", fullname);
		session.setAttribute("profileEmail", email);
		session.setAttribute("profilePhone", phone);
		session.setAttribute("profilePassword", password);
		session.setAttribute("profileAvatar", avatar);
		String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
		session.setAttribute("profileOtp", otp);
		try {
			EmailUtils.sendOTP(user.getEmail(), otp);
			resp.sendRedirect(req.getContextPath() + "/verify-otp");
		} catch (Exception e) {
			clearPendingProfile(session);
			req.setAttribute("user", editUser);
			req.setAttribute("edit", true);
			req.setAttribute("error", "Không thể gửi OTP. Thông tin chưa được cập nhật.");
			req.getRequestDispatcher("/views/member/myaccount.jsp").forward(req, resp);
		}
	}

	private User getAccount(HttpServletRequest req) {
		Object account = req.getSession(false) == null ? null : req.getSession(false).getAttribute("account");
		return account instanceof User ? (User) account : null;
	}

	private String uploadAvatar(HttpServletRequest req) throws IOException, ServletException {
		Part part = req.getPart("avatar");
		if (part == null || part.getSize() == 0 || part.getSubmittedFileName() == null) return null;
		String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		int dot = original.lastIndexOf('.');
		if (dot < 1 || !IMAGE_EXTENSIONS.contains(original.substring(dot + 1).toLowerCase())) {
			throw new ServletException("Avatar phải là file ảnh JPG, PNG, GIF hoặc WEBP");
		}
		String name = "Avatar." + System.currentTimeMillis() + original.substring(dot).toLowerCase();
		File directory = new File(Constant.DIR);
		if (!directory.exists() && !directory.mkdirs()) throw new IOException("Không thể tạo thư mục upload");
		part.write(new File(directory, name).getAbsolutePath());
		return name;
	}

	private void showEdit(HttpServletRequest req, HttpServletResponse resp, User user, String error) throws ServletException, IOException {
		req.setAttribute("user", user);
		req.setAttribute("edit", true);
		req.setAttribute("error", error);
		req.getRequestDispatcher("/views/member/myaccount.jsp").forward(req, resp);
	}

	private void clearPendingProfile(HttpSession session) {
		for (String key : new String[] { "verifyMode", "profileUserId", "profileUsername", "profileFullname", "profileEmail", "profilePhone", "profilePassword", "profileAvatar", "profileOtp" }) session.removeAttribute(key);
	}

	private String value(HttpServletRequest req, String name) {
		String value = req.getParameter(name);
		return value == null ? "" : value.trim();
	}

	private String nullToEmpty(String value) { return value == null ? "" : value; }
}
