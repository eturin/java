package common;

public class UserProf {
    public UserProf(String login, String pwd, String name, String eMail){
        this.login=login;
        this.login=login;
        this.eMail=eMail;
        this.name =name;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    private String login;
    private String pwd;
    private String name;
    private String eMail;


}
