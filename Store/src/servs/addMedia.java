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
import store.Media;
import store.User;

/**
 * Servlet implementation class addMedia
 */
@WebServlet("/addMedia")
public class addMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addMedia() {
		super();
		// Wrong need to use Servlet's Container's DB Connection pool
		// TODO:Use connection pool
		DBIO.init();
		DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite");
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> pm = request.getParameterMap();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		PrintWriter resWrite = response.getWriter();
		// Going to return JSON so set header
		response.setHeader("Content-Type", "application/json");
		resWrite.write("[");
		if (user != null && user instanceof Manager) {
			//Util variable to hold user as a manager
			Manager man = (Manager) user;
			Media addedMedia = null; //Holds updated Media Object after successful DB call
			if (Util.validateParams(pm, "id", "num")) {
				Media gottenMedia =DBIO.getMedia(Integer.parseInt(pm.get("id")[0])); 
				if(gottenMedia!=null){
					addedMedia = man.addMedia(
							gottenMedia,
							Integer.parseInt(pm.get("num")[0]));
					//Write out media object with update num
					resWrite.write(Util.mediaToJson(addedMedia));
						
				}
				
				
			} else if (Util.validateParams(pm, "creator", "name", "duration",
					"genre", "price", "numRatings", "avgRating", "num")) {
				addedMedia = man
						.addMedia(
								new Media(
										pm.get("creator")[0],
										pm.get("name")[0],
										Integer.parseInt(pm.get("duration")[0]),
										pm.get("genre")[0],
										Double.parseDouble(pm.get("price")[0]),
										Integer.parseInt(pm.get("numRatings")[0]),
										Double.parseDouble(pm.get("avgRating")[0]),
										0), Integer.parseInt(pm.get("num")[0]));
				//Write out new media object
				resWrite.write(Util.mediaToJson(addedMedia));
			}else{
				resWrite.write("{\"Error\":\"Invalid Parameters\"}");
			}
		}else{
			resWrite.write("{\"Error\":\"Not a manager\"}");
		}
		resWrite.write("]");
		Util.closeRes(response, resWrite);
	}

}
