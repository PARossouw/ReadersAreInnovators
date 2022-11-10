package Category.Service;

import Category.Model.Category;
import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import java.util.List;

public class CategoryServiceRepoImpl extends JDBCConfig implements CategoryServiceRepo {

    @Override
    public List<Category> displayAllCategories() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean addCategoryToStory(Category category, Story story) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
