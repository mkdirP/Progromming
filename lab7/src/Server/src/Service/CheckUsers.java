package Service;

import Common.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CheckUsers {
    HashMap<Integer, User> users = new HashMap<>();
    private User u;

    public void addUsers(User u){

        users.put(users.size(), u);
    }


//    //验证用户是否有效的方法
//    public boolean checkUser(String userId, String passwd){
//
//        User user = users.get(userId);
//        if (user == null){
//            return false;
//        }
//        if (!user.getPasswd().equals(passwd)) {
//            //id正确，密码错误
//            return false;
//        }
//        return true;
//    }
}
