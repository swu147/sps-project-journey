package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Pattern;
import org.jsoup.Jsoup;
// import org.jsoup.safety.Whitelist;
import org.jsoup.safety.Safelist;


@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the value entered in the form.
    // String textValue = request.getParameter("text-input");
    String textValue = Jsoup.clean(request.getParameter("text-input"), Safelist.basic());
    long timestamp = System.currentTimeMillis();
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Emails");

    if(!validEmail(textValue)){
        response.getWriter().println("I can't believe you've done this.");
        return;
    }

    FullEntity taskEntity = 
        Entity.newBuilder(keyFactory.newKey())
            .set("timestamp",timestamp)
            .set(
            "description",textValue)
            .build();

    datastore.put(taskEntity);

    response.sendRedirect("https://bongo.cat/");
    // Print the value so you can see it in the server logs.
    System.out.println("You submitted: " + textValue);

    // Write the value to the response so the user can see it.
    response.getWriter().println("You submitted: " + textValue);
  }

  public Boolean validEmail(String email){
    String reg = "^(.+)@(.+)$";
    Pattern pattern = Pattern.compile(reg);

    return pattern.matcher(email).matches();
  }
}