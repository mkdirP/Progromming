//package Service;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
///**
// * 组织集合的存储
// */
//public class ObjectData {
//    private String url = "jdbc:postgresql://localhost:5432/studs";
//    private String user = "postgres";
//    private String password = "s336262";
//    public void objectPutData(SerializableComArg sca){
//        Connection conn = null;
//        PreparedStatement stat = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(url, user, password);
//            //专门执行操作数据库语句的对象
//            stat = conn.prepareStatement("INSERT INTO studs.public.usertable(user_id,username,password) VALUES (nextval('sequence_name'),?,?);");
//            //专门执行DML语句的，返回值是“影响数据库中的记录条数”
//            stat.setObject(1, u.getUserId());
//            stat.setObject(2, u.getPasswd());
//            stat.execute();
//
//            String s1 = sca.getComArg();
//
//            String[] commands = {"help", "info", "show", "clear",
//                    "exit", "history"};
//            for (int i = 0; i < commands.length; i++) {
//                if (s1.equals(commands[i])) {
//
//                }
//            }
//            String[] commands2 = {"add", "update_id", "remove_by_id", "execute_script", "remove_greater",
//                    "remove_lower", "remove_all_by_genre", "filter_contains_name", "filter_starts_with_name"};
//            for (int i = 0; i < commands2.length; i++) {
//                if (s1.split(" ")[0].equals(commands2[i])) {
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (stat != null) {
//                    stat.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
//
//
//try {
//        Class.forName("org.postgresql.Driver");
//        conn = DriverManager.getConnection(url, user, password);
//        } catch (Exception e) {
//        e.printStackTrace();
//        }finally {
//        try {url
//        if (stat != null) {
//        stat.close();
//        }
//        } catch (SQLException e) {
//        e.printStackTrace();
//        }
//        try {
//        if (conn != null) {
//        conn.close();
//        }
//        } catch (SQLException e) {
//        throw new RuntimeException(e);
//        }
//        }
