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

public class SignUp extends HttpServlet {
    private common.Account acc;
    public SignUp(common.Account acc){
        this.acc=acc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        common.UserProf up=acc.getUserProf(req.getSession().getId());
        if(up!=null){
            //пользователь уже авторизован (регистрация не нужна)
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            //возможна регистрация
            BufferedWriter bwr = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
            bwr.write(PageMaker.getPage("reg.html", null));
            bwr.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        String strLogin=req.getParameter("login"   );
        String strPwd  =req.getParameter("password");
        String strName =req.getParameter("name"    );
        String strEMail=req.getParameter("eMail"   );
        common.UserProf up=acc.addUserProf(strLogin,strPwd,strName,strEMail);
        if(up==null) {
            //пользователь не смог зарегистрироваться
            resp.getWriter().print("Unauthorized");
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            //успешная регистрация
            resp.getWriter().print("Authorized: "+up.getName());
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_OK);
            acc.addSession(req.getSession().getId(),up);
        }
    }
}
