import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    //рассположение статических файлов веб-сервера
    private final static String PATH_TO_TEMPLATES="TEMPLATES";

    public static void main(String[] args) {
        //формируем обработчики запросов
        ServletContextHandler servletContextHandler=new ServletContextHandler(ServletContextHandler.SESSIONS); //параметр конструктора позволит генерировать ключ сессии
        servletContextHandler.addServlet(new ServletHolder(new Chat()),"/chat");
        //стандартный обработчик, отдающий файлы из каталога template
        ResourceHandler resourceHandler=new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(PATH_TO_TEMPLATES);

        //создаем сервер, передаем обработчики и запускаем
        Server server=new Server(8080);
        server.setHandler(new HandlerList(new Handler[]{resourceHandler,servletContextHandler}));
        try{
            server.start();
            System.out.println("Server started");
            //главный поток далее ждет поток сервера
            server.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
