package servlet;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

public class Admin extends HttpServlet {
    private int limit;
    public Admin() throws Exception{
        limit=10;
        MBeanServer mBeanServer= ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(new AccountServerController(this),new ObjectName("Admin:type=AccountServerController"));//первая часть имени как директория, а вторая как имя файла
    }
    public int getLimit(){
        return limit;
    }
    public void setLimit(int val){
        limit=val;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter=resp.getWriter();
        printWriter.print(limit);
        printWriter.flush();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
