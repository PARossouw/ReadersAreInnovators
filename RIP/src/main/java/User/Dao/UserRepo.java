package User.Dao;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;
import java.util.List;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.Writer;

public interface UserRepo {

    Boolean createUser(User user) throws SQLException;//for creating readers and editors

    User getUser(User user) throws SQLException;

    Boolean becomeWriter(User user) throws SQLException;

    Boolean deleteUser(User user) throws SQLException;

    

}
