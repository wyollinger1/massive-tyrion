package servs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import store.DBIO;
import store.Manager;
import store.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "login", urlPatterns = { "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();

		//Wrong need to use Servlet's Container's DB Connection pool
		//TODO:Use connection pool
		DBIO.init();
		DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> paramMap = request.getParameterMap();
		User user = null;
		HttpSession session = null;
		boolean isManager = false;
		boolean loggedIn = false;
		
		PrintWriter resWrite= response.getWriter();
		// Going to return JSON so set header
		response.setHeader("Content-Type", "application/json");
		

		// Login user
		if (paramMap.containsKey("uname")
				&& paramMap.get("uname")[0].length() > 0
				&& paramMap.containsKey("password")
				&& paramMap.get("password")[0].length() > 0) {
			user = DBIO.login(paramMap.get("uname")[0],
					paramMap.get("password")[0]);
			if (user != null) {
				if (user instanceof Manager) {
					isManager = true;
				}
				loggedIn = true;
				session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("isManager", isManager);
				session.setAttribute("loggedIn", loggedIn);
				resWrite.write(String.format("[{\"name\":%s,\"balance\":%.2f,"+
				"\"id\":%d}]", user.getName(), user.getBalance(),
				user.getID()));
				resWrite.flush();
				resWrite.close();
				response.flushBuffer();
				return;
			}
		}
		resWrite.write("[]");
		resWrite.flush();
		resWrite.close();
		response.flushBuffer();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
