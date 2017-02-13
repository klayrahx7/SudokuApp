package cpsc463sudoku.sudoku;

/**
 * Created by Mark Ballin on 2/12/2017.
 */
public class User {
    private long userID;
    private String userName;
    private String userPassword;

    public User()
    {
        this.userID = -1;
        this.userName = "";
        this.userPassword = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
