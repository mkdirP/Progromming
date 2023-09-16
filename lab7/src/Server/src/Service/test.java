package Service;

public class test {
    public static void main(String[] args) {
        Useru u = new Useru();
        u.setPwd("7777777");
        u.setUserId("888");
        abc abc = new abc();
        abc.get(u);
        abc.sout();

    }
}
class abc{
    Useru user = new Useru();
    public Useru get(Useru u){
        String id = u.getUserId();
        String pwd = u.toString();
        user.setUserId(id);
        user.setPwd(pwd);
        return user;
    }

    public void sout(){
        System.out.println(get(user).getUserId());
    }
}

class Useru{
    private String userId;
    private String pwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
