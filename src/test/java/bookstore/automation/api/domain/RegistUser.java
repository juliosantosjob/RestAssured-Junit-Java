package bookstore.automation.api.domain;

public class RegistUser {
    private String userName;
    private String password;

    public RegistUser() {}

    public RegistUser(String userName, String password) {
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
