package Category.Dao;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;
import java.util.List;
import user.Reader.Model.Reader;

public interface CategoryRepo {

    Boolean createCategory(Category category) throws SQLException;

    Category retrieveCategory(int CategoryID) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

    List<Category> getPreferredCategories(Reader reader) throws SQLException;

    Boolean addPreferredCategories(Reader reader, List<Category> categories) throws SQLException;
    
    List<Category> getStoryCategories(Story story) throws SQLException;
    

}
