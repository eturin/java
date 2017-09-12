import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends HttpServlet {
    //свойства
    private DB db;
    //конструктор
    public SignUp(DB db){
        this.db=db;
    }
    //методы
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String login=req.getParameter("login");
        String password=req.getParameter("password");
        if(db.signUp(login,password))
            resp.setStatus(HttpServletResponse.SC_OK);
        else
            resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
    }
}
