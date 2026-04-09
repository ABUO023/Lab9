import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class UserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        UserManager manager = UserManager.getInstance();

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            if (username != null && !username.trim().isEmpty()) {
                manager.addUser(username);
                HttpSession session = request.getSession(true);
                session.setAttribute("username", username);
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.println("<response>");
                out.println("<status>success</status>");
                out.println("<message>Login successful</message>");
                out.println("</response>");
            } else {
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.println("<response>");
                out.println("<status>error</status>");
                out.println("<message>Invalid username</message>");
                out.println("</response>");
            }
        } else if ("logout".equals(action)) {
            String username = (String) request.getSession().getAttribute("username");
            if (username != null) {
                manager.removeUser(username);
                request.getSession().invalidate();
            }
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<response>");
            out.println("<status>success</status>");
            out.println("<message>Logout successful</message>");
            out.println("</response>");
        } else if ("getUsers".equals(action)) {
            List<String> users = manager.getOnlineUsers();
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<usersList>");
            for (String user : users) {
                out.println("<user>" + escapeXml(user) + "</user>");
            }
            out.println("</usersList>");
        }

        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private String escapeXml(String str) {
        if (str == null) return "";
        return str.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&apos;");
    }
}
