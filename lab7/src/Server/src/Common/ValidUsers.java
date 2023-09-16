package Common;

import java.util.HashMap;

/**
 * 存储用户
 */
public class ValidUsers {
    HashMap<Integer,User> userHashMap = new HashMap<>();
    private String userId;
    private String passwd;

    public ValidUsers() {
    }

    public ValidUsers(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public void addValidUsers(){
        int i = userHashMap.size();
        userHashMap.put(i,new User(userId, passwd));
    }
}
