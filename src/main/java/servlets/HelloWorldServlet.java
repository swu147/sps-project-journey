package servlets;

import com.google.gson.Gson;
import data.Station;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    double latitude = 33.823385;
    double longitude = -84.369357;
    String stationName = "Lindbergh Center";
    Integer id = 1;

    Station station = new Station(longitude, latitude, stationName, id);
    Gson gson = new Gson();
    String json = gson.toJson(station);

    // response.setContentType("text/html;");
    // response.getWriter().println("<h1>Hello world!</h1>");

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
