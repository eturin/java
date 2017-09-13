import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Chat extends WebSocketServlet {
    private final static long TIME_OUT = 1 * 60 * 60 * 1000; //1 час

    //служебный тип, связанный с конкретными соединениями
    private class Connect implements WebSocketListener{
        //свойства
        private Session session;
        //конструктор
        public Connect(ServletUpgradeRequest req, ServletUpgradeResponse resp){

        }
        //методы
        @Override
        public void onWebSocketConnect(Session session) {
            //сразу после установки соединения
            this.session=session;
            addConn(this);
            sendAll("Присоединился новый участник");
        }

        @Override
        public void onWebSocketClose(int i, String s) {
            //разрыв соединения
            delConn(this);
            sendAll("Вышел");
        }

        @Override
        public void onWebSocketError(Throwable throwable) {
            //ошибка соединения
            delConn(this);
            sendAll("Вышел");
        }

        @Override
        public void onWebSocketText(String msg) {
            //получено текстовое сообщение
            sendAll(msg);
        }

        @Override
        public void onWebSocketBinary(byte[] payload, int offset, int len) {
            //получено бинарное сообщение
        }

        public void sendString(String msg) {
            //записываем сообщение в сокет клиента
            try {
                session.getRemote().sendString(msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    //свойства
    private Set<Connect> connectSet;

    //конструктор
    public Chat(){
        //здесь потоко-безопасная структура данных
        connectSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
    private void addConn(Connect connect){
        //добавляем новое соединение в коллекцию рассылки
        connectSet.add(connect);
    }
    private void delConn(Connect connect){
        //удалаем соединение из коллекции рассылки
        connectSet.remove(connect);
    }
    private void sendAll(String msg){
        //рассылка сообщений всей коллекции
        for(Connect connect:connectSet)
            connect.sendString(msg);
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        //закрывать соединение после длительного бездействия
        webSocketServletFactory.getPolicy().setIdleTimeout(TIME_OUT);
        //регистрируем обработчик новых соединений
        webSocketServletFactory.setCreator((req, resp) -> new Connect(req, resp));
    }
}
