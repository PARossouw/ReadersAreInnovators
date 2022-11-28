package Category.Dao;

import Category.Model.Category;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.List;
import User.Model.Reader;
import User.Model.User;

public interface CategoryRepo {

    Boolean createCategory(Category category) throws SQLException;

    Category retrieveCategory(Category category) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

    List<Category> getPreferredCategories(User reader) throws SQLException;

    Boolean addPreferredCategories(Reader reader, List<Category> categories) throws SQLException;
    
    List<Category> getStoryCategories(Story story) throws SQLException;
    
    List<Category> topCategoriesForMonth() throws SQLException;
    
    Boolean addCategoriesToStory(Story story, List<Category> categories) throws SQLException;

}