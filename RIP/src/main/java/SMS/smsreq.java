package SMS;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class smsreq {

    //private Date datetime;
    private String datetime;
    private String user;
    private String pass;
    private String msisdn;
    private String message;

    public smsreq() {
    }

    public smsreq(Date daettime, String user, String pass, String msisdn, String message) {
        this.datetime = datetime;
        this.user = user;
        this.pass = pass;
        this.msisdn = msisdn;
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SMS{" + "datetime=" + datetime + ", user=" + user + ", pass=" + pass + ", msisdn=" + msisdn + ", message=" + message + '}';
    }

}
