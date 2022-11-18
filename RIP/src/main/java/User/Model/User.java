package User.Model;

import java.util.Calendar;

public class User {

    private Integer UserID;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Boolean isActive;
    private Calendar dateAdded;
    private Integer roleID;

    public User() {
    }

    public User(Integer UserID, String username, String email, String password, Boolean isActive, Calendar dateAdded) {
        this.UserID = UserID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.dateAdded = dateAdded;
    }

    public User(Integer UserID, String username, String email, String phoneNumber, String password, Boolean isActive, Calendar dateAdded) {
        this.UserID = UserID;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActive = isActive;
        this.dateAdded = dateAdded;
    }
    
    

    public User(Integer UserID, String username, String email, String phoneNumber, String password, Boolean isActive, Calendar dateAdded, Integer roleID) {
        this.UserID = UserID;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActive = isActive;
        this.dateAdded = dateAdded;
        this.roleID = roleID;

    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Calendar getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        return "User{" + "UserID=" + UserID + ", username=" + username + ", email=" + email + ", phoneNumber=" + phoneNumber + ", password=" + password + ", isActive=" + isActive + ", dateAdded=" + dateAdded + ", roleID=" + roleID + '}';
    }

}
