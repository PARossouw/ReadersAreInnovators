package Category.Dao;

import Category.Model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryRepo {

    Boolean createCategory(Category category) throws SQLException;

    Category retrieveCategory(int CategoryID) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

}
