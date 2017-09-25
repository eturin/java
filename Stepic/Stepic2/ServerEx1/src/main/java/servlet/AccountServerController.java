package servlet;

public class AccountServerController implements AccountServerControllerMBean{
    Admin admin;
    public AccountServerController(Admin admin){
        this.admin=admin;
    }
    @Override
    public int getUsersLimit() {
        return admin.getLimit();
    }
    @Override
    public void setUsersLimit(int val) {
        admin.setLimit(val);
    }
}