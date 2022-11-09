package User.Dao;

import JDBCConfig.JDBCConfig;
import User.Model.User;
import java.sql.SQLException;
import user.Editor.Model.Editor;
import user.Reader.Model.Reader;
import user.Writer.Model.Writer;


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

            }else if (user instanceof Editor && getConnection() != null) {

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
            ps = getConnection().prepareStatement("select userid, email, phonenumber, password, isactive, dateadded, role from user where username = ?");
            ps.setString(1, user.getUsername());

            rs = ps.executeQuery();

            if (rs.next()) {
                int userID = (rs.getInt("userid"));
                String email = (rs.getString("email"));
                String phoneNumber = (rs.getString("phonenumber"));
                String password = (rs.getString("password"));
                boolean isActive = (rs.getBoolean(""));
                String date = (rs.getString(""));
                int role = (rs.getInt(""));                     //how to get date out
                
                switch(role) {
                
                    case 1 :
                        u = new Reader();
                        break;
                    case 2 :
                        u = new Writer();
                        break;
                    case 3 :
                        u = new Editor();
                        break;
          //          case 4 :
          //              u = new AdminEditor();
          //              break;
                    default :
                        
                        break;
                }
                
                u.setUserID(userID);
                u.setUsername(user.getUsername());
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

    private void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
    }
}