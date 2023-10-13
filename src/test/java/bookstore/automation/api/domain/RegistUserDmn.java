package bookstore.automation.api.domain;

public class RegistUserDmn {
    private String userName;
    private String password;

    public RegistUserDmn() {}

    public RegistUserDmn(String userName, String password) {
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
