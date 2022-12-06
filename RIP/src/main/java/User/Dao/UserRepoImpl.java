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
            if (con != null) {
                if (user.getRoleID() == 3) {
                    ps = con.prepareStatement("insert into User (username, email, password, role) values (?, ?, ?, ?)");
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setInt(4, user.getRoleID());
                } else {
                    ps = con.prepareStatement("insert into User (username, email, password) values (?, ?, ?)");
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                }
                rowsAffected = ps.executeUpdate();
            }
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
                        + "phonenumber, password, isactive, dateAdded, role, isBlocked"
                        + " from user where username = ?");
                ps.setString(1, user.getUsername());

                rs = ps.executeQuery();

                while (rs.next()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateAdded"));

                    switch (rs.getInt("role")) {
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
                    u.setUserID(rs.getInt("userid"));
                    u.setRoleID(rs.getInt("role"));
                    u.setUsername(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    u.setPhoneNumber(rs.getString("phonenumber"));
                    u.setPassword(rs.getString("password"));
                    u.setIsActive(rs.getBoolean("isactive"));
                    u.setDateAdded(calendar);
                }
            }
        } finally {
            close();
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
    public Map<String, Integer> topWriters() throws SQLException {//this method must return the username as the key so i can send it back from the servlet and get a full writer object
        con = DBManager.getConnection();
        Map<String, Integer> topWriters = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select username, role, u.isactive, sum(views) as allViews from user u "
                        + "inner join story s on u.userid = s.writer group by writer order by allViews desc limit 30");
                rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getInt("role") == 2) {
                        topWriters.put(rs.getString("username"), rs.getInt("allViews"));
                    }
                }
            }
        } finally {
            close();
        }
        return topWriters;
    }

    @Override
    public Map<String, Integer> topRejectedWritersForMonth() throws SQLException {
        con = DBManager.getConnection();
        Map<String, Integer> topRejectedWriters = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select username, role, count(action) "
                        + "as timesRejected from user u inner join story s on u.userid = s.writer inner "
                        + "join story_transaction st on st.user = u.userid where st.action like '%rejected%' "
                        + "and month(actionperformedon) = month(current_timestamp) "
                        + "and year(actionperformedon) = year(current_timestamp) group by userid order by timesRejected desc limit 5;");
                rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getInt("role") == 2) {

                        topRejectedWriters.put(rs.getString("username"), rs.getInt("timesRejected"));
                    }
                }
            }
        } finally {
            close();
        }
        return topRejectedWriters;
    }

    @Override
    public Map<String, Integer> topApprovingEditors() throws SQLException {

        con = DBManager.getConnection();
        Map<String, Integer> editorList = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select username, role,"
                        + "count(st.action) as timesApproved from user u "
                        + "inner join story_transaction st on u.userid = st.user "
                        + "where st.action like '%approved%' group by username order by timesApproved desc");
                rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getInt("role") == 3) {
                        editorList.put(rs.getString("username"), rs.getInt("timesApproved"));//put the toString there temporarily
                        if (editorList.size() == 3) {
                            break;
                        }
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

                    writer.setRoleID(rs.getInt("role"));
                    writer.setUserID(rs.getInt("userid"));
                    writer.setUsername(rs.getString("username"));
                    writer.setEmail(rs.getString("email"));
                    writer.setPhoneNumber(rs.getString("phonenumber"));
                    writer.setPassword(rs.getString("password"));
                    writer.setIsActive(rs.getBoolean("isactive"));

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
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateadded"));

                    switch (rs.getInt("role")) {

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
                    u.setUserID(rs.getInt("userid"));
                    u.setRoleID(rs.getInt("role"));
                    u.setUsername(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    u.setPhoneNumber(rs.getString("phonenumber"));
                    u.setPassword(rs.getString("password"));
                    u.setIsActive(rs.getBoolean("isactive"));
                    u.setDateAdded(calendar);
                }
            }
        } finally {
            close();
        }
        return u;
    }

    @Override
    public User getUserByUserID(User user) throws SQLException {

        con = DBManager.getConnection();

        User u = new User();

        try {
            if (con != null) {

                ps = con.prepareStatement("select userid, username, email, "
                        + "phonenumber, password, isactive, dateadded, role from user "
                        + "where userid = ?");
                ps.setInt(1, user.getUserID());

                rs = ps.executeQuery();

                if (rs.next()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateadded"));

                    switch (rs.getInt("role")) {

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
                    u.setUserID(rs.getInt("userid"));
                    u.setRoleID(rs.getInt("role"));
                    u.setUsername(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    u.setPhoneNumber(rs.getString("phonenumber"));
                    u.setPassword(rs.getString("password"));
                    u.setIsActive(rs.getBoolean("isactive"));
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
