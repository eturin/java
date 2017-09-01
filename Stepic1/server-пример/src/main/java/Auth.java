import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Auth extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> var=createMap(req);
        var.put("message","");
        BufferedWriter bwr = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
        bwr.write(PageGenerator.instance().getPage("p.html",var));
        bwr.flush();
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> var=createMap(req);
        String msg=req.getParameter("message");
        if(msg==null || msg.isEmpty())
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            resp.setStatus(HttpServletResponse.SC_OK);
        var.put("message",msg==null ? "": msg);
        BufferedWriter bwr = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
        bwr.write(PageGenerator.instance().getPage("p.html",var));
        bwr.flush();
        resp.setContentType("text/html;charset=utf-8");

    }

    private Map<String,Object> createMap(HttpServletRequest req){
        Map<String,Object> var=new HashMap<>();
        var.put("method",     req.getMethod());
        var.put("URL",        req.getRequestURL().toString());
        var.put("pathInfo",   req.getPathInfo());
        var.put("sessionId",  req.getSession().getId());
        var.put("parameters", req.getParameterMap().toString());

        return var;
    }
}
