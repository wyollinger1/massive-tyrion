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

		// Wrong need to use Servlet's Container's DB Connection pool
		// TODO:Use connection pool
		DBIO.init();
		DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite");
	}

	/**
	 * Same as doPost, logs on user
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @see Login#logon(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logon(request, response);
	}

	/**
	 * Same as doGet, logs on user
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @see Login#logon(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logon(request, response);
	}

	/**
	 * Actual logon logic for servlets doPost and doGet
	 * @param request the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 * @see Login#doPost(HttpServletRequest, HttpServletResponse)
	 * @see Login#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void logon(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> paramMap = request.getParameterMap();
		User user = null;
		HttpSession session = null;
		boolean isManager = false;
		boolean loggedIn = false;

		PrintWriter resWrite = response.getWriter();
		// Going to return JSON so set header and begin the list
		response.setHeader("Content-Type", "application/json");
		resWrite.write("[");
		
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
				resWrite.write(Util.userToJson(user));
			}else{
				resWrite.write("{\"Error\":\"Username and password mismatch\"}");
			}
		}else{
			resWrite.write("{\"Error\":\"Invalid Parameters\"}");
		}
		
		//Wrap it up
		resWrite.write("]");
		Util.closeRes(response, resWrite);
	}

	/**
	 * This be the logout just invalidates the session.
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
