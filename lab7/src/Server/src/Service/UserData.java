package Service;

import Common.User;

import java.sql.*;

/**
 * username = userId
 */

public class UserData {
    //    private String username;
//    private String pwd;
//
//    public UserData(String username, String pwd) {
//        this.username = username;
//        this.pwd = pwd;
//    }
    private User u;
    private String url = "jdbc:postgresql://localhost:5432/studs";
    private String user = "postgres";
    private String password = "s336262";

    public void userPutData(User u) {

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            //专门执行操作数据库语句的对象
            stat = conn.prepareStatement("INSERT INTO studs.public.usertable(user_id,username,password) VALUES (nextval('sequence_name'),?,?);");
            //专门执行DML语句的，返回值是“影响数据库中的记录条数”
            stat.setObject(1, u.getUserId());
            stat.setObject(2, u.getPasswd());
            stat.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * @param u
     * @return 检查用户是否在数据库中
     */
    public boolean UserCheckData(User u){
        boolean f = false;
        Connection conn = null;
        Statement stat = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            String sql = "select username,password from studs.public.usertable where username=" + "'" + u.getUserId() + "'" +
                    "and password=" + "'" + u.getPasswd() + "'";


//            stat.execute();
            ResultSet rs = stat.executeQuery(sql);
            if(!rs.next()){
                //查询结果为空时
                f = false;
            }else {
                f = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return f;
    }
}

