import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SignIn extends HttpServlet {
    //свойства
    private DB db;
    //конструктор
    public SignIn(DB db){
        this.db=db;
    }
    //методы
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String login=req.getParameter("login");
        String password=req.getParameter("password");
        PrintWriter printWriter = resp.getWriter();
        if(db.signIn(login,password)) {
            printWriter.print("Authorized: "+login);
            printWriter.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }else {
            printWriter.print("Unauthorized");
            printWriter.flush();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
