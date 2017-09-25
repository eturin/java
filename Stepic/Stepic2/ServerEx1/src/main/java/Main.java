import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception{
        //устанавливаем обработчики
        ServletContextHandler servletContextHandler=new ServletContextHandler(ServletContextHandler.SESSIONS);//для генерации ключа сессий
        servletContextHandler.addServlet(new ServletHolder(new servlet.Admin()),"/admin");

        //создаем сервер
        Server server=new Server(8080);
        server.setHandler(servletContextHandler);

        //запускаем
        server.start();
        System.out.println("Server started");

        //ожидаем завершение главного потока сервера
        server.join();

    }
}
