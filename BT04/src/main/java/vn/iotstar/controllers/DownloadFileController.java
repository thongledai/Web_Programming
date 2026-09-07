package vn.iotstar.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = "/image")
public class DownloadFileController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getParameter("fname");

		if (fileName == null || fileName.isBlank()) {
			return;
		}

		File file = new File(Constant.DIR, fileName);

		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String contentType = getServletContext().getMimeType(file.getName());

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		resp.setContentType(contentType);

		try (FileInputStream input = new FileInputStream(file)) {
			input.transferTo(resp.getOutputStream());
		}
	}
}