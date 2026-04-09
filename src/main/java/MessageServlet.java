import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class MessageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        MessageManager manager = MessageManager.getInstance();

        if ("sendMessage".equals(action)) {
            String username = request.getParameter("username");
            String message = request.getParameter("message");
            
            if (username != null && message != null && !message.trim().isEmpty()) {
                manager.addMessage(username, message);
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.println("<response>");
                out.println("<status>success</status>");
                out.println("</response>");
            } else {
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.println("<response>");
                out.println("<status>error</status>");
                out.println("</response>");
            }
        }

        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        MessageManager manager = MessageManager.getInstance();

        if ("getMessages".equals(action)) {
            String lastIndexStr = request.getParameter("lastIndex");
            int lastIndex = 0;
            try {
                lastIndex = Integer.parseInt(lastIndexStr);
            } catch (NumberFormatException e) {
                lastIndex = 0;
            }

            List<MessageManager.Message> messages = manager.getMessages(lastIndex);
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<messagesList>");
            
            for (MessageManager.Message msg : messages) {
                out.println("<message>");
                out.println("<sender>" + escapeXml(msg.sender) + "</sender>");
                out.println("<text>" + escapeXml(msg.text) + "</text>");
                out.println("</message>");
            }
            
            out.println("</messagesList>");
        }

        out.close();
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
