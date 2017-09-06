import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            //проверка наличия
            Class.forName("org.sqlite.JDBC");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        //подключение
        Path path=Paths.get("\\\\AS-MSK-N7060\\errors$\\.statistic.db");
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:"+path.toString())){
            System.out.println("Соединение установлено");

            try(Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( "select * from tasks as t;" )){
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i=0, l=rsmd.getColumnCount();i<l;++i)
                    System.out.print("|"+rsmd.getColumnName(i+1));
                System.out.println("|");

                while ( rs.next() ) {
                    for(int i=0, l=rsmd.getColumnCount();i<l;++i)
                        System.out.print("|"+rs.getString(i+1));
                    System.out.println("|");
                }
            }

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }
}
