package pl.edu.uw.dsk.dev.wallboard;

public class LoginInfo {
    private String username;
    private String password;
    LoginInfo(String username, String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
