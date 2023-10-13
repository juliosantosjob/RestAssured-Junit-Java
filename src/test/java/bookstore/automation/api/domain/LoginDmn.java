package bookstore.automation.api.domain;

public class LoginDmn {
    private String userName;
    private String password;

    public LoginDmn() {}

    public LoginDmn(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
