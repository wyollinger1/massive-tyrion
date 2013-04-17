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

import store.Customer;
import store.DBIO;
import store.Manager;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public User() {
		super();
		// Wrong need to use Servlet's Container's DB Connection pool
		// TODO:Use connection pool
		DBIO.init();
		DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite");
	}

	/**
	 * Get the user's information by it's id.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> pm = request.getParameterMap();
		HttpSession session = request.getSession();
		store.User user = (store.User) session.getAttribute("user");

		PrintWriter resWrite = response.getWriter();
		// Going to return JSON so set header
		response.setHeader("Content-Type", "application/json");
		resWrite.write("[");
		
		//Check for valid input and grab user info
		if (user != null && user instanceof Manager) {
			if(Util.validateParams(pm, "id")){
				store.User gottenUser= (store.User)DBIO.getUser(Integer.parseInt(pm.get("id")[0]));
				if(gottenUser!=null && gottenUser instanceof Customer){
					//man.getcustInfo(gottenUser); I want JSON not a string
					resWrite.write(Util.userToJson(gottenUser));
				}else{
					//Either a manager's id or non-existent
					resWrite.write("{\"Error\":\"Invalid Id\"}");
				}
			}else{
				resWrite.write("{\"Error\":\"Invalid Parameters\"}");
			}
		}else{
			resWrite.write("{\"Error\":\"Not a manager\"}");
		}
		
		//Finish up
		resWrite.write("]");
		Util.closeRes(response, resWrite);
	}
}
