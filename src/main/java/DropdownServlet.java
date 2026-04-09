import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DropdownServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Return a list of dropdown values in XML format
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<values>");
        out.println("<value>alpha</value>");
        out.println("<value>bravo</value>");
        out.println("<value>tango</value>");
        out.println("</values>");

        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
