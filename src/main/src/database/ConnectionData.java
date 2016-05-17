package database;

/**
 * Created by mond on 10.05.2016.
 * This class is just a container to simplify the connection. All needed data to connect to the DB are held here.
 */
public class ConnectionData {
    // Default values are already set.
    private  String username = "root";
    private  String password = "c34atu4";

    private String driver = "com.mysql.cj.jdbc.Driver";
    private  String url = "jdbc:mysql://localhost:3306/dbprog" + "?serverTimezone=America/New_York&useSSL=false";

    public ConnectionData() {
    }

    public ConnectionData(String username, String password, String driver, String url) {
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }
}
