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

/**
 * Servlet implementation class Sales
 */
@WebServlet("/Sales")
public class Sales extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sales() {
        super();
        // Wrong need to use Servlet's Container's DB Connection pool
     	// TODO:Use connection pool
     	DBIO.init();
     	DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite");
    }

	/**
	 * Get total sales of the store or if an media id is passed in the 'id' parameter then get 
	 * the number sold of that particular item.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> pm = request.getParameterMap();
		HttpSession session = request.getSession();
		store.User user = (store.User) session.getAttribute("user");
		Integer numSold=0; // Holds total number sold of an individual item
		Double totalSales=0.0; //Used to hold total sales of store

		PrintWriter resWrite = response.getWriter();
		// Going to return JSON so set header
		response.setHeader("Content-Type", "application/json");
		resWrite.write("[");
		
		//Check for valid input and grab user info
		if (user != null && user instanceof Manager) {
			//Utility variable to access manager functionality
			Manager man = (Manager)user;
			//If there is an id parameter then get number sold for that item
			if(Util.validateParams(pm, "id")){
				store.Media mObj = DBIO.getMedia(Integer.parseInt(pm.get("id")[0]));
				if(mObj!=null){
					numSold=man.getnumSold(mObj);
					resWrite.write(String.format("{\"media\":%s,\"numSold\":\"%d\"}",
							Util.mediaToJson(mObj), numSold));
				}else{
					resWrite.write("{\"Error\":\"Invalid Id\"}");
				}
			//Otherwise we're getting the total sales for the store
			}else{ 
				totalSales=man.getTotalSales();
				resWrite.write(String.format("{\"totalSales\":%.2f}", totalSales));
			}
		}else{
			resWrite.write("{\"Error\":\"Not a manager\"}");
		}
		
		//Wrap it up
		resWrite.write("]");
		Util.closeRes(response, resWrite);
	}

	/**
	 * Purchases content
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> pm = request.getParameterMap();
		HttpSession session = request.getSession();
		store.User user = (store.User) session.getAttribute("user");
		int isPurchased;
		

		PrintWriter resWrite = response.getWriter();
		// Going to return JSON so set header
		response.setHeader("Content-Type", "application/json");
		resWrite.write("[");
		
		//TODO:Add try catch for parseInt's NumberFormatException -- can return Invalid Parameters JSON error
		//Try and purchase item
		if(Util.validateParams(pm, "mId", "numToBuy")){
			store.Media mObj=DBIO.getMedia(Integer.parseInt(pm.get("mId")[0]));
			isPurchased=user.purchase(mObj, Integer.parseInt(pm.get("numToBuy")[0]));
			resWrite.write(String.format("{\"isPurchased\":%b, \"user\":%s}", isPurchased>0, Util.userToJson(user)));
		}else{
			resWrite.write("{\"Error\":\"Invalid Parameters\"}");
		}
		
		//Wrap it up
		resWrite.write("]");
		Util.closeRes(response, resWrite);
	}

}
