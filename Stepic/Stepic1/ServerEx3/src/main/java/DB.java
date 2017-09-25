import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;

public class DB {
    //фабрика
    private SessionFactory sessionFactory;
    //конструктор
    DB(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.setProperty("hibernate.dialect"                , "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url"         , "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username"    , "");
        configuration.setProperty("hibernate.connection.password"    , "");
        configuration.setProperty("hibernate.show_sql"               , "true");
        configuration.setProperty("hibernate.hbm2ddl.auto"           , "update");

        /*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);*/
        sessionFactory = configuration.buildSessionFactory();
    }
    //методы
    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getServiceRegistry().getService( ConnectionProvider.class ).getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
    public boolean signUp(String login, String password){
        if(login==null || password==null)
            return false;
        try(Session session=sessionFactory.openSession()) {
            //поиск пользователя по login
            Query query = session.createQuery("FROM UsersDataSet AS D where login='"+login+"'");
            if(!query.list().isEmpty())
                return false;

            //добавление пользователя
            Transaction transaction=session.beginTransaction();
            session.save(new UsersDataSet(login,password));
            transaction.commit();
        }
        return true;
    }
    public boolean signIn(String login, String password){
        if(login==null || password==null)
            return false;
        try(Session session=sessionFactory.openSession()){
            //поиск пользователя по login
            Query query = session.createQuery("FROM UsersDataSet AS D where login='"+login+"'");
            if(query.list().isEmpty())
                return false;
            else if(!((UsersDataSet)query.list().get(0)).getPassword().equals(password))
                return false;
        }
        return true;
    }
}
