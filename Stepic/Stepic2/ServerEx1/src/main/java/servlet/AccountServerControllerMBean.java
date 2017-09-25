package servlet;

@SuppressWarnings("UnusedDeclaration")
//имя должно заканчиваться на MBean
public interface AccountServerControllerMBean{
    public int getUsersLimit();
    public void setUsersLimit(int val);
}
