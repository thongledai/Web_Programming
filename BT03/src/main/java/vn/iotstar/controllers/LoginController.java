package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.models.UserModel;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/login" })

public class LoginController extends HttpServlet {


private static final long serialVersionUID = 1L;

IUserService service = new UserServiceImpl();

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	resp.setContentType("text/html");

	resp.setCharacterEncoding("UTF-8");

	req.setCharacterEncoding("UTF-8");

	// lấy tham số từ view

	String username = req.getParameter("username");

	String password = req.getParameter("password");

	String remember = req.getParameter("remember");

	// kt tham số

	boolean isRememberMe = false;

	if ("on".equals(remember)) {

		isRememberMe = true;
	}

	String alertMsg = "";

	if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {

		alertMsg = "Password cannot be empty";

		req.setAttribute("passwordError", alertMsg);

		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);

		return;
	}

	// xử lý

	UserModel user = service.login(username, password);

	if (user != null) {

		HttpSession session = req.getSession(true);

		session.setAttribute("account", user);

		if (isRememberMe) {

			saveRemeberMe(resp, username);
		}

		resp.sendRedirect(req.getContextPath() + "/home");

	} else {

		alertMsg = "Incorrect account or password";

		req.setAttribute("passwordError", alertMsg);

		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

}

private void saveRemeberMe(HttpServletResponse resp, String username) {

	Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);

	cookie.setMaxAge(30 * 60);

	resp.addCookie(cookie);
}


}
