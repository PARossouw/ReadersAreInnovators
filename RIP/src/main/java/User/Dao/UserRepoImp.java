package User.Dao;

import JDBCConfig.JDBCConfig;
import User.Model.AdminEditor;
import User.Model.User;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.Writer;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImp extends JDBCConfig implements UserRepo {

    @Override
    public Boolean createUser(User user) throws SQLException {

        if (user instanceof Editor && getConnection() != null) {

            ps = getConnection().prepareStatement("insert into User (username, email, password, role) values (?, ?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, 3);
            rowsAffected = ps.executeUpdate();

        } else if (user instanceof Editor && getConnection() != null) {

            ps = getConnection().prepareStatement("insert into User (username, email, password) values (?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            rowsAffected = ps.executeUpdate();

        }
        close();

        return rowsAffected == 1;
    }

    @Override
    public User getUser(User user) throws SQLException {
        User u = null;

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select userid, username, email, phonenumber, password, isactive, dateadded, role from user where ? = ?");
            if (user.getUsername() != null) {
                ps.setString(1, "Username");
                ps.setString(2, user.getUsername());
            } else if (user.getEmail() != null) {
                ps.setString(1, "Email");
                ps.setString(2, user.getEmail());
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                int userID = (rs.getInt("userid"));
                String username = (rs.getString("username"));
                String email = (rs.getString("email"));
                String phoneNumber = (rs.getString("phonenumber"));
                String password = (rs.getString("password"));
                boolean isActive = (rs.getBoolean("isactive"));
                Date dateAdded = rs.getDate("dateadded");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateAdded);
                int role = (rs.getInt("role"));

                switch (role) {

                    case 1:
                        u = new Reader();
                        break;
                    case 2:
                        u = new Writer();
                        break;
                    case 3:
                        u = new Editor();
                        break;
                    case 4:
                        u = new AdminEditor();
                        break;
                    default:

                        break;
                }

                u.setUserID(userID);
                u.setUsername(username);
                u.setEmail(email);
                u.setPhoneNumber(phoneNumber);
                u.setPassword(password);
                u.setIsActive(isActive);
                // u.setDateAdded();

            }

        }

        close();
        return u;
    }

    @Override
    public Boolean becomeWriter(User user) throws SQLException {
        if (getConnection() != null) {

            ps = getConnection().prepareStatement("update user set role = 2 where userid = ?");
            ps.setInt(1, user.getUserID());
            rowsAffected = ps.executeUpdate();

        }
        close();

        return rowsAffected == 1;
    }

    @Override
    public Boolean deleteUser(User user) throws SQLException {
        if (getConnection() != null) {

            ps = getConnection().prepareStatement("update user set isActive = 0 where userid = ?");
            ps.setInt(1, user.getUserID());
            rowsAffected = ps.executeUpdate();

        }
        close();

        return rowsAffected == 1;
    }

    @Override
    public List<Writer> TopWriters() throws SQLException {
        List<Writer> writerList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select userid, username, email, phonenumber, password, u.isactive, dateadded, role, sum(views) as views from user u inner join story s on u.userid = s.writer order by count(views) desc");
            rs = ps.executeQuery();

            while (rs.next()) {

                rs = ps.executeQuery();

                while (rs.next()) {
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    boolean isActive = (rs.getBoolean("isactive"));
                    Date dateAdded = rs.getDate("dateadded");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateAdded);
                    int role = (rs.getInt("role"));
                    int views = rs.getInt("views");

                    if (role == 2) {
                        Writer w = new Writer();
                        w.setUserID(userID);
                        w.setUsername(username);
                        w.setEmail(email);
                        w.setPhoneNumber(phoneNumber);
                        w.setPassword(password);
                        w.setIsActive(isActive);
                        w.setDateAdded(calendar);
                        writerList.add(w);
                    }
                }
            }
            close();

        }

        return writerList;
    }

    @Override
    public List<Writer> TopRejectedWritersForMonth() throws SQLException {
        List<Writer> writerList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select userid, username, email, phonenumber, password, u.isactive, dateadded, role, count(action) as amount from user u inner join story s on u.userid = s.writer inner join story_transaction st on s.writer = st.story where st.action like '%rejected%' and month(actionperformedon) = month(current_timestamp) and year(actionperformedon) = year(current_timestamp) order by count(action) desc");
            rs = ps.executeQuery();
            while (rs.next()) {

                rs = ps.executeQuery();

                while (rs.next()) {
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    boolean isActive = (rs.getBoolean("isactive"));
                    Date dateAdded = rs.getDate("dateadded");
                    Calendar date = Calendar.getInstance();
                    date.setTime(dateAdded);
                    int role = (rs.getInt("role"));
                    int amount = rs.getInt("amount");

                    if (role == 2) {
                        Writer w = new Writer();
                        w.setUserID(userID);
                        w.setUsername(username);
                        w.setEmail(email);
                        w.setPhoneNumber(phoneNumber);
                        w.setPassword(password);
                        w.setIsActive(isActive);
                        w.setDateAdded(date);
                        writerList.add(w);
                    }
                }
            }
            close();

        }

        return writerList;
    }

    @Override
    public List<Editor> topApprovingEditors() throws SQLException {
        List<Editor> editorList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select u.userid, username, email, phonenumber, password, u.isactive, dateadded, role, count(action) as amount from user u inner join story_transaction st on u.userid = st.user where st.action like '%approved%' order by count(action) desc");
            rs = ps.executeQuery();
            while (rs.next()) {

                rs = ps.executeQuery();

                while (rs.next()) {
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    boolean isActive = (rs.getBoolean("isactive"));
                    Date dateAdded = rs.getDate("dateadded");
                    Calendar date = Calendar.getInstance();
                    date.setTime(dateAdded);
                    int role = (rs.getInt("role"));
                    int amount = rs.getInt("amount");

                    if (role == 2) {
                        Editor e = new Editor();
                        e.setUserID(userID);
                        e.setUsername(username);
                        e.setEmail(email);
                        e.setPhoneNumber(phoneNumber);
                        e.setPassword(password);
                        e.setIsActive(isActive);
                        e.setDateAdded(date);
                        editorList.add(e);
                    }
                }
            }
            close();

        }

        return editorList;
    }

    @Override
    public Boolean blockWriter(Writer writer) throws SQLException {
        if (getConnection() != null) {

            ps = getConnection().prepareStatement("update user set role = 1, isBlocked = 1 where userid = ?");
            ps.setInt(1, writer.getUserID());
            rowsAffected = ps.executeUpdate();

        }
        close();

        return rowsAffected == 1;
    }

}
