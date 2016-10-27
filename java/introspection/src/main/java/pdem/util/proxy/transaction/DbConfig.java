package pdem.util.proxy.transaction;

/**
 *
 *
 * @author pdemanget
 * @version 4 avr. 2016
 */
public class DbConfig {

  private boolean enable=true;
  private String driver;
  private String url;
  private String user;
  private String password;


  public DbConfig (String driver, String url, String user, String password) {
    super();
    this.driver = driver;
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public DbConfig () {
    super();
  }

  public String getDriver () {
    return driver;
  }
  public String getUrl () {
    return url;
  }
  public String getUser () {
    return user;
  }
  public String getPassword () {
    return password;
  }
  public void setDriver (String driver) {
    this.driver = driver;
  }
  public void setUrl (String url) {
    this.url = url;
  }
  public void setUser (String user) {
    this.user = user;
  }
  public void setPassword (String password) {
    this.password = password;
  }
  @Override
  public String toString () {
    return "DbConfig [driver=" + driver + ", url=" + url + ", user=" + user + "]";
  }

  public boolean isEnable () {
    return enable;
  }

  public void setEnable (boolean enable) {
    this.enable = enable;
  }


}
