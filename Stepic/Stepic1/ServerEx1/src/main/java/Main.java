import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception{
        //связываем с контекстом обработчик запросов к /mirror
        ServletContextHandler cont=new ServletContextHandler(ServletContextHandler.SESSIONS);
        cont.addServlet(new ServletHolder(new TestServlet()),"/mirror");

        //создаем сервер и связываем его с портом
        Server srv=new Server(8080);
        srv.setHandler(cont);

        //начинаем прослушивать порт
        srv.start();
        System.out.println("Server started");

        //останавливаем главный поток
        srv.join();
    }
}
