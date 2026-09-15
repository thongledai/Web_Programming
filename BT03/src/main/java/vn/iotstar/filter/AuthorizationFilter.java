package vn.iotstar.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;

@WebFilter({ "/admin/*", "/manager/*", "/user/*" })
public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession(false);

		// Chưa đăng nhập
		if (session == null || session.getAttribute("account") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		User user = (User) session.getAttribute("account");

		String url = req.getRequestURI();

		// Admin
		if (url.startsWith(req.getContextPath() + "/admin")) {
			if (user.getRoleid() != 1) {
				resp.sendRedirect(req.getContextPath() + "/waiting");
				return;
			}
		}

		// Manager
		else if (url.startsWith(req.getContextPath() + "/manager")) {
			if (user.getRoleid() != 2) {
				resp.sendRedirect(req.getContextPath() + "/waiting");
				return;
			}
		}

		// User
		else if (url.startsWith(req.getContextPath() + "/user")) {
			if (user.getRoleid() != 3) {
				resp.sendRedirect(req.getContextPath() + "/waiting");
				return;
			}
		}

		// Cho phép truy cập
		chain.doFilter(request, response);
	}
}