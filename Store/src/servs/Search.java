package servs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import store.*;
import servs.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet(description = "search", urlPatterns = { "/search" })
public class Search extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public Search() {
    // Wrong need to use Servlet's Container's DB Connection pool
    // TODO:Use connection pool
    DBIO.init();
    DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite");
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    DBIO.Types mType = null;
    DBIO.SearchField sField = null;
    String searchStr = "";
    String json = "[]";
    ArrayList<store.Media> results = null;
    Map<String, String[]> paramMap = request.getParameterMap();

    // Going to return JSON so set header
    response.setHeader("Content-Type", "application/json");

    // Grab params if there
    if (paramMap.containsKey("sField") && paramMap.containsKey("mType")) {
      if (paramMap.get("sField").length > 0) {
        sField = Util.strToSearchField(paramMap.get("sField")[0]);
      }
      if (paramMap.get("mType").length > 0) {
        mType = Util.strToType(paramMap.get("mType")[0]);
      }
      if (paramMap.containsKey("search") && paramMap.get("search").length > 0) {
        searchStr = paramMap.get("search")[0];
      }
    }
    // If I could get at least the mType and sField params
    // and they were valid give a non-empty response
    if (mType != null && sField != null) {
      json = "[";
      results = Customer.search(searchStr, sField, mType);
      for (Iterator<store.Media> iter = results.iterator(); iter.hasNext();) {
        json += Util.mediaToJson(iter.next());
        json += ",";
      }
      if (json.length() > 1) { // Don't want to remove the '['
        json = json.substring(0, json.length() - 1);
      }
      json += "]";

    }
    response.getWriter().write(json);
    response.flushBuffer();

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
  }

}
