package Service;

import Common.User;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

public class SHA {
    public static void putData(User u){
        String pwd = "";
        String url = "jdbc:postgresql://localhost:5432/studs";
        String user = "postgres";
        String password = "s336262";

//        try {
//            Class.forName("org.postgresql.Driver");
//
//            Connection conn = DriverManager.getConnection(url, user, password);
//
//            //专门执行操作数据库语句的对象
//            Statement stat = conn.createStatement();
//            String sql = "select password from usertable where username="+"'" + u.getUserId()+"'";
//            //专门执行DML语句的，返回值是“影响数据库中的记录条数”
//            ResultSet rs = stat.executeQuery(sql);
//            while (rs.next()) {
//                pwd = rs.getString("password");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String passwordToHash = pwd;
        byte[] salt = new byte[0];
        try {
            salt = SHA.getSalt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String securePassword = SHA.get_SHA_384_SecurePassword(passwordToHash, salt);

        Connection conn = null;
        Statement stat = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            //专门执行操作数据库语句的对象
            String sql = "update usertable set sha=" + "'"+ securePassword + "'" +" WHERE usertable.username="+ "'"+ u.getUserId()+"'";
            //专门执行DML语句的，返回值是“影响数据库中的记录条数
            stat=conn.createStatement();
            stat.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String get_SHA_384_SecurePassword(String passwordToHash, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
