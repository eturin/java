package common;

import java.util.HashMap;
import java.util.Map;

public class Account {
    //структуры данных, для хранения сведений о сессии профилях
    private Map<String, UserProf> mpUserProf, mpSessions;
    public Account(){
        mpUserProf=new HashMap<>();
        mpSessions=new HashMap<>();
    }
    public void addSession(String strId,UserProf up){
        mpSessions.put(strId,up);
    }
    public void delSession(String strId){
        mpSessions.remove(strId);
    }
    public UserProf addUserProf(String login, String pwd, String name, String eMail){
        if(login==null || pwd==null)
            return null;

        UserProf up=mpUserProf.get(login);
        if(up!=null && up.getPwd()!=pwd)
            return null;

        up=new UserProf(login,pwd,name,eMail);
        mpUserProf.put(login,up);

        return up;
    }
    public UserProf getUserProf(String strId){
        return mpSessions.get(strId);
    }
    public UserProf getUserProf(String strLogin, String strPwd){
        if(strLogin==null || strPwd==null)
            return null;

        UserProf up=mpUserProf.get(strLogin);
        if(up!=null && up.getPwd()==strPwd){
            return up;
        }else
            return null;
    }
}
