public class User {
    private String userName;
    private String userPwd;
    private int userId;
    private String userType;

    public User(String userType, String userName, String userPwd, int userId){
        setUserType(userType);
        setUserId(userId);
        setUserName(userName);
        setUserPwd(userPwd);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
