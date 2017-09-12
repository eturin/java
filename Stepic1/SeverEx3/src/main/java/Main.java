import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(final String[] args) throws Exception {
        //создаем экземпляр, работающий с базой
        DB db=new DB();
        //db.printConnectInfo();

        //создаем набор сервлетов
        ServletContextHandler servletContextHandler=new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new SignUp(db)),"/signup");
        servletContextHandler.addServlet(new ServletHolder(new SignIn(db)),"/signin");

        //создаем сервер и связываем его с сервлетами
        Server server=new Server(8080);
        server.setHandler(servletContextHandler);
        //запускаем сервер
        server.start();
        System.out.println("Server started");

        //ожидаем основной поток сервера
        server.join();
    }
}