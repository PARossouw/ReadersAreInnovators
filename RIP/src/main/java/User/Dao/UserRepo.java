package User.Dao;

import User.Model.Editor;
import User.Model.User;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserRepo {

    Boolean createUser(User user) throws SQLException;//for creating readers and editors

    User getUser(User user) throws SQLException;

    Boolean becomeWriter(User user) throws SQLException;

    Boolean deleteUser(User user) throws SQLException;

    Map<Writer, Integer> topWriters() throws SQLException;

    Map<Writer, Integer> topRejectedWritersForMonth() throws SQLException;

    Map<Editor, Integer> topApprovingEditors() throws SQLException;

    Boolean blockWriter(Writer writer) throws SQLException;

    List<Writer> writerSearch(String writerSearch) throws SQLException;
    
    User getUserWithUsername(User user) throws SQLException;

}
