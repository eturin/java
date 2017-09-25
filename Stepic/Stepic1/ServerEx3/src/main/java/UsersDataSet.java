import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable {
    //свойства
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "password", unique = true)
    private String password;

    //конструктор без параметров для Hibernate!
    public UsersDataSet() {
    }
    public UsersDataSet(String login, String password){
        this.login=login;
        this.password=password;
    }

    //методы
    public long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
