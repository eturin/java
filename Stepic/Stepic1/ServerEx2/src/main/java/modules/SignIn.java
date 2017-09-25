package modules;

import common.PageMaker;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class SignIn extends HttpServlet {
    private common.Account acc;

    public SignIn(common.Account acc){
        this.acc=acc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        common.UserProf up=acc.getUserProf(req.getSession().getId());
        if(up!=null){
            //пользователь уже авторизован
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            //требуется авторизация
            BufferedWriter bwr = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
            bwr.write(PageMaker.getPage("auth.html", null));
            bwr.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        String strLogin=req.getParameter("login"   );
        String strPwd  =req.getParameter("password");
        common.UserProf up=acc.getUserProf(strLogin,strPwd);
        if(up==null) {
            //пользователь не зарегистрирован или не верный пароль
            resp.getWriter().print("Unauthorized");
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            //успешная авторизация
            resp.getWriter().print("Authorized: "+up.getName());
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_OK);
            acc.addSession(req.getSession().getId(),up);
        }
    }
}
