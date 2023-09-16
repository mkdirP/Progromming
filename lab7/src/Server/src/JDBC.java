import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/studs";
            String user = "postgres";
            String password = "s336262";
            Connection conn = DriverManager.getConnection(url,user,password);
            System.out.println(conn);

            //专门执行操作数据库语句的对象
            Statement stat = conn.createStatement();
            String sql = "insert into studs.public.usertable(username,password) values('aaa','bbb')";
            //专门执行DML语句的，返回值是“影响数据库中的记录条数”
            int i = stat.executeUpdate(sql);
            System.out.println(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}