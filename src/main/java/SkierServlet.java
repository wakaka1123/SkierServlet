import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("application/json");
    String urlPath = req.getPathInfo();
    Gson gson = new Gson();
    Status status = new Status();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty() || urlPath.equals("/")) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      status.setMessage("missing parameters");
      res.getOutputStream().print(gson.toJson(status));
      res.getOutputStream().flush();
      //res.getWriter().write("missing parameters");
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      status.setMessage("Invalid URL");
      res.getOutputStream().print(gson.toJson(status));
      res.getOutputStream().flush();
      //res.getWriter().write("Invalid URL");
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
      int resVal = 200;
      res.getOutputStream().print(gson.toJson(resVal));
      res.getOutputStream().flush();
      //res.getWriter().write("It works!");
    }

  }

  private boolean isUrlValid(String[] urlParts) {
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/days/1/skiers/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    if (urlParts.length != 8 || !urlParts[2].equals("seasons") || !urlParts[4].equals("days")
        || !urlParts[6].equals("skiers")) {
      return false;
    }

    try {
      int resortID = Integer.parseInt(urlParts[1]);
      String seasonID = urlParts[3];
      String dayID = urlParts[5];
      int skierID = Integer.parseInt(urlParts[7]);
      return true;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("application/json");
    String urlPath = req.getPathInfo();
    Gson gson = new Gson();
    Status status = new Status();

    if (urlPath == null || urlPath.isEmpty() || urlPath.equals("/")) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      status.setMessage("missing parameters");
      res.getOutputStream().print(gson.toJson(status));
      res.getOutputStream().flush();
      //res.getWriter().write("missing parameters");
      return;
    }

    String[] urlParts = urlPath.split("/");

    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      status.setMessage("Invalid URL");
      res.getOutputStream().print(gson.toJson(status));
      res.getOutputStream().flush();
      //res.getWriter().write("Invalid URL");
    } else {

      try {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = req.getReader().readLine()) != null) {
          sb.append(s);
        }
        //Request request = (Request) gson.fromJson(sb.toString(), Request.class);
        //TODO: post to and get res from database,
        // if data not found return 404 , if succeeds return 200

        // handle valid request
        res.setStatus(HttpServletResponse.SC_CREATED);
        status.setMessage("you're all set");
        res.getOutputStream().print(gson.toJson(status));
        res.getOutputStream().flush();

        // TODO: handle data not found

      } catch (Exception e) {
        e.printStackTrace();
        // return exception code when exception risen from url parsing
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        status.setMessage(e.getMessage());
        res.getOutputStream().print(gson.toJson(status));
        res.getOutputStream().flush();
      }

    }


  }
}