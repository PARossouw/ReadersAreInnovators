package Category.Dao;

import Category.Model.Category;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.List;
import User.Model.Reader;
import User.Model.User;
import java.util.HashMap;

public interface CategoryRepo {

    Boolean createCategory(Category category) throws SQLException;

    Category retrieveCategory(Category category) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

    List<Category> getPreferredCategories(User reader) throws SQLException;

    Boolean addPreferredCategories(Reader reader, List<Category> categories) throws SQLException;
    
    Boolean addPreferredCategoriesToUser(Reader reader) throws SQLException;
    
    List<Category> getStoryCategories(Story story) throws SQLException;
    
    HashMap<String, Integer> topCategoriesForMonth(String month) throws SQLException;
    
    Boolean addCategoriesToStory(Story story, List<Category> categoryList) throws SQLException;

}