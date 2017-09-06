package common;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Main {
    public static String PATH_TO_TEMPLATES="template";
    public static void main(String[] args) throws Exception {
        common.Account acc=new common.Account();

        //стандартный обработчик, отдающий файлы из каталога template
        ResourceHandler res=new ResourceHandler();
        res.setResourceBase(PATH_TO_TEMPLATES);

        //формируем специальные обработчики запросов
        ServletContextHandler cont=new ServletContextHandler(ServletContextHandler.SESSIONS);//параметр здесь нужен для формирования идентификатора сессии
        cont.addServlet(new ServletHolder(new modules.SignUp(acc)),"/signup");
        cont.addServlet(new ServletHolder(new modules.SignIn(acc)),"/signin");

        //складываем обработчики в список в указанном порядке
        HandlerList hl=new HandlerList();
        hl.setHandlers(new Handler[]{res,cont});

        //создаем сервер и связываем с ним обработчики и менеджер сессий
        Server srv=new Server(8080);
        //ServerConnector http = new ServerConnector(srv);
        //http.setHost("127.0.0.1");
        //http.setPort(8080);
        //srv.setConnectors(new Connector[] { http });
        srv.setHandler(hl);

        //запускаем прослушивание
        srv.start();
        System.out.println("Server started");
        //присоединяем к главному потоку поток сервера
        srv.join();

    }
}
