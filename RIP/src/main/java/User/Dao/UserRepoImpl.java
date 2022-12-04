package User.Dao;

import DBManager.DBManager;
import User.Model.AdminEditor;
import User.Model.User;
import java.sql.SQLException;
import java.util.Calendar;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepoImpl implements UserRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public Boolean createUser(User user) throws SQLException {

        con = DBManager.getConnection();

        try {
//            if (user instanceof Editor && con != null) {
//
//                ps = con.prepareStatement("insert into User (username, email, password, role) values (?, ?, ?, ?)");
//                ps.setString(1, user.getUsername());
//                ps.setString(2, user.getEmail());
//                ps.setString(3, user.getPassword());
//                ps.setInt(4, 3);
//                rowsAffected = ps.executeUpdate();
//
//            } else if (user instanceof Reader && con != null) {

            ps = con.prepareStatement("insert into User (username, email, password) values (?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            rowsAffected = ps.executeUpdate();
//            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public User getUser(User user) throws SQLException {

        con = DBManager.getConnection();
        User u = new User();

        try {
            if (con != null) {

                ps = con.prepareStatement("select userid, username, email, "
                        + "phonenumber, password, isactive,role from user "
                        + "where username = ?");
                ps.setString(1, user.getUsername());
                //ps.setString(2, user.getEmail());

                rs = ps.executeQuery();

                while (rs.next()) {
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    Boolean isActive = (rs.getBoolean("isactive"));

//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(rs.getDate("dateadded"));
                    Integer role = (rs.getInt("role"));

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
                            u = new Reader();
                    }

                    u.setUserID(userID);
                    u.setRoleID(role);
                    u.setUsername(username);
                    u.setEmail(email);
                    u.setPhoneNumber(phoneNumber);
                    u.setPassword(password);
                    u.setIsActive(isActive);
                    //u.setDateAdded(calendar);

                }
                if (u == null) {   // Prevents a null pointer exception in the service layer. 
                    u.setUsername("");
                    u.setEmail("");
                }

                return u;

            }
        } finally {
            close();
        }

        close();
        if (u == null) {   // Prevents a null pointer exception in the service layer. 
            u.setUsername("");
            u.setEmail("");
        }

        return u;
    }

    @Override
    public Boolean becomeWriter(User user) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("update user set role = 2 where userid = ?");
                ps.setInt(1, user.getUserID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Boolean deleteUser(User user) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("update user set isActive = 0 where userid = ?");
                ps.setInt(1, user.getUserID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Map<Writer, Integer> topWriters() throws SQLException {
        con = DBManager.getConnection();
        Map<Writer, Integer> topWriters = new HashMap<>();

        try {
            if (con != null) {  //Confirm sql call once database is populated
                ps = con.prepareStatement("select userid, username, email, phonenumber, password, "
                        + "u.isactive, dateadded, role, sum(views) as allViews from user u "
                        + "inner join story s on u.userid = s.writer order by allViews desc");
                rs = ps.executeQuery();

                while (rs.next()) {

                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    boolean isActive = (rs.getBoolean("isactive"));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateadded"));

                    int role = (rs.getInt("role"));
                    int views = rs.getInt("allViews");

                    if (role == 2) {
                        Writer writer = new Writer();
                        writer.setUserID(userID);
                        writer.setUsername(username);
                        writer.setEmail(email);
                        writer.setPhoneNumber(phoneNumber);
                        writer.setPassword(password);
                        writer.setIsActive(isActive);
                        writer.setDateAdded(calendar);
                        topWriters.put(writer, views);
                    }
                }
            }
        } finally {
            close();
        }
        return topWriters;
    }

    @Override
    public Map<Writer, Integer> topRejectedWritersForMonth() throws SQLException {
        con = DBManager.getConnection();
        Map<Writer, Integer> topRejectedWriters = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select userid, username, email, phonenumber, password, "
                        + "u.isactive, dateadded, role, count(action) as timesRejected from user u "
                        + "inner join story s on u.userid = s.writer "
                        + "inner join story_transaction st on s.storyID = st.story "
                        + "where st.action like '%rejected%' and "
                        + "month(actionperformedon) = month(current_timestamp) and year(actionperformedon) = year(current_timestamp) "
                        + "order by timesRejected desc limit 30");
                rs = ps.executeQuery();

                while (rs.next()) {

                    rs = ps.executeQuery();
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    boolean isActive = (rs.getBoolean("isactive"));

                    Calendar date = Calendar.getInstance();
                    date.setTime(rs.getDate("dateadded"));

                    int role = (rs.getInt("role"));
                    int rejectedCount = rs.getInt("timesRejected");

                    if (role == 2) {
                        Writer writer = new Writer();
                        writer.setUserID(userID);
                        writer.setUsername(username);
                        writer.setEmail(email);
                        writer.setPhoneNumber(phoneNumber);
                        writer.setPassword(password);
                        writer.setIsActive(isActive);
                        writer.setDateAdded(date);
                        topRejectedWriters.put(writer, rejectedCount);
                    }
                }
            }
        } finally {
            close();
        }
        return topRejectedWriters;
    }

    @Override
    public Map<Editor, Integer> topApprovingEditors() throws SQLException {

        con = DBManager.getConnection();
        Map<Editor, Integer> editorList = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select u.userid, username, email, phonenumber, password, "
                        + "u.isactive, dateadded, role, count(action) as timesApproved from user u "
                        + "inner join story_transaction st on u.userid = st.user "
                        + "where st.action like '%approved%' order by timesApproved desc limit 3");
                rs = ps.executeQuery();

                while (rs.next()) {

                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    boolean isActive = (rs.getBoolean("isactive"));

                    Calendar date = Calendar.getInstance();
                    date.setTime(rs.getDate("dateadded"));

                    int role = (rs.getInt("role"));
                    int timesApproved = rs.getInt("timesApproved");

                    if (role == 2) {
                        Editor editor = new Editor();
                        editor.setUserID(userID);
                        editor.setUsername(username);
                        editor.setEmail(email);
                        editor.setPhoneNumber(phoneNumber);
                        editor.setPassword(password);
                        editor.setIsActive(isActive);
                        editor.setDateAdded(date);
                        editorList.put(editor, timesApproved);
                    }
                }
            }
        } finally {
            close();
        }
        return editorList;
    }

    @Override
    public Boolean blockWriter(Writer writer) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("update user set role = 1, isBlocked = 1 where userid = ?");
                ps.setInt(1, writer.getUserID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;

    }

    @Override
    public List<Writer> writerSearch(String writerSearch) throws SQLException {

        con = DBManager.getConnection();

        List<Writer> writers = new ArrayList<>();
//        writer.setUsername(writerSearch);
//        writers.add(writer);
//        return writers;

        try {
            if (con != null) {

                ps = con.prepareStatement("select userid, username, email, "
                        + "phonenumber, password, isactive, dateadded, role from user "
                        + "where username like ? and role = 2");

                writerSearch = "%" + writerSearch + "%";
                ps.setString(1, writerSearch);

                rs = ps.executeQuery();

                while (rs.next()) {
                    Writer writer = new Writer();
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    Boolean isActive = (rs.getBoolean("isactive"));

                    //watch out for calendar
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(rs.getDate("dateadded"));
                    Integer role = (rs.getInt("role"));

                    writer.setRoleID(role);
                    writer.setUserID(userID);
                    writer.setUsername(username);
                    writer.setEmail(email);
                    writer.setPhoneNumber(phoneNumber);
                    writer.setPassword(password);
                    writer.setIsActive(isActive);
//                u.setDateAdded(calendar);

                    writers.add(writer);
                }
            }
        } finally {
            close();
        }
        return writers;
    }

    @Override
    public User getUserWithUsername(User user) throws SQLException {

        con = DBManager.getConnection();

        User u = new User();

        try {
            if (con != null) {

                ps = con.prepareStatement("select userid, username, email, "
                        + "phonenumber, password, isactive, dateadded, role from user "
                        + "where username = ?");
                ps.setString(1, user.getUsername());

                rs = ps.executeQuery();

                if (rs.next()) {
                    int userID = (rs.getInt("userid"));
                    String username = (rs.getString("username"));
                    String email = (rs.getString("email"));
                    String phoneNumber = (rs.getString("phonenumber"));
                    String password = (rs.getString("password"));
                    Boolean isActive = (rs.getBoolean("isactive"));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateadded"));
                    Integer role = (rs.getInt("role"));

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
                            u = new Reader();
                    }

                    u.setUserID(userID);
                    u.setRoleID(role);
                    u.setUsername(username);
                    u.setEmail(email);
                    u.setPhoneNumber(phoneNumber);
                    u.setPassword(password);
                    u.setIsActive(isActive);
                    u.setDateAdded(calendar);
                }
            }
        } finally {
            close();
        }
        return u;
    }

    public void close() throws SQLException {

        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (con != null) {
            con.close();
        }
    }

}

//hardcoding
//        List<Writer> writers = new ArrayList<>();
//        Writer writer1 = new Writer();
//        writer1.setUsername("Anton");
//        Writer writer2 = new Writer();
//        writer2.setUsername("Buffy");
//        
//        writers.add(writer1);
//        writers.add(writer2);
//        return writers;

