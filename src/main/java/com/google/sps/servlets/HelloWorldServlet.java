package com.google.sps.servlets;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

    private String convertToJsonUsingGson(ArrayList bingbong) {
        Gson gson = new Gson();
        String json = gson.toJson(bingbong);
        return json;
        }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<String> bing = new ArrayList<String>();
    bing.add("Bingbong1");
    bing.add("Bingbong2");
    bing.add("Bingbong3");

    String json = convertToJsonUsingGson(bing);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
