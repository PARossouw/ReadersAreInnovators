package Category.Dao;

import Category.Model.Category;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.List;
import User.Model.Reader;
import java.util.Calendar;

public interface CategoryRepo {

    Boolean createCategory(Category category) throws SQLException;

    Category retrieveCategory(int CategoryID) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

    List<Category> getPreferredCategories(Reader reader) throws SQLException;

    Boolean addPreferredCategories(Reader reader, List<Category> categories) throws SQLException;
    
    List<Category> getStoryCategories(Story story) throws SQLException;
    
    List<Category> topCategoriesForMonth(Calendar month) throws SQLException;

}
