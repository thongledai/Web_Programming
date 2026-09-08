package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		boolean isRememberMe = "on".equals(remember);

		if (username == null || username.trim().isEmpty()
				|| password == null || password.trim().isEmpty()) {

			req.setAttribute("passwordError", "Username hoặc password không được để trống");
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		User user = service.FindByUserName(username);

		if (user == null) {
			req.setAttribute("passwordError", "Incorrect account or password");
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		if (!password.equals(user.getPassword())) {
			req.setAttribute("passwordError", "Incorrect account or password");
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		if (user.getStatus() == 0) {
			req.setAttribute("passwordError", "Tài khoản chưa được kích hoạt");

			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		HttpSession session = req.getSession(true);
		session.setAttribute("account", user);

		if (isRememberMe) {
			saveRemeberMe(resp, username);
		}

		resp.sendRedirect(req.getContextPath() + "/waiting");
	}

	private void saveRemeberMe(HttpServletResponse resp, String username) {

		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);

		cookie.setMaxAge(30 * 60);

		resp.addCookie(cookie);
	}
}
