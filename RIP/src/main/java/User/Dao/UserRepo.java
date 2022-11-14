package User.Dao;

import User.Model.Editor;
import User.Model.User;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public interface UserRepo {

    Boolean createUser(User user) throws SQLException;//for creating readers and editors

    User getUser(User user) throws SQLException;

    Boolean becomeWriter(User user) throws SQLException;

    Boolean deleteUser(User user) throws SQLException;

    List<Writer> TopWriters() throws SQLException;

    List<Writer> TopRejectedWritersForMonth() throws SQLException;

    List<Editor> topApprovingEditors() throws SQLException;

    Boolean blockWriter(Writer writer) throws SQLException;

}
