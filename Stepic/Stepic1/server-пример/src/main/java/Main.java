import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Main {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context= new ServletContextHandler(ServletContextHandler.SESSIONS);

        //указываем обработчик запросов к http://127.0.0.1:8080/*
        Auth auth = new Auth();
        context.addServlet(new ServletHolder(auth),"/*");

        //создаем сервер и связываем его с портом
        Server srv=new Server(8080);
        srv.setHandler(context);
        srv.start();
        srv.join();
    }
}
