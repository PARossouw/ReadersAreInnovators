package Category.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryServiceImpl extends JDBCConfig implements CategoryService {

    private CategoryRepo categoryRepo;

    @Override
    public List<Category> displayAllCategories() {

        List<Category> allCategories = new ArrayList<>();
        try {
            return allCategories = categoryRepo.getAllCategories();
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allCategories;
    }

    @Override
    public String addCategoriesToStory(List<Category> categories, Story story) {
        try {
            if(categories.isEmpty()){
                return "No categories provided. Provide categories to add to story";
            }
            return categoryRepo.addCategoriesToStory(story, categories)?"Categories successfully added to Story":"Unable to add Categories";
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Could not complete the request at the moment, please try again later.";
    }

    @Override
    public List<Category> topCategoriesForTheMonth() {

        List<Category> categoryList = new ArrayList<>();
        try {
            return categoryList = categoryRepo.topCategoriesForMonth();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryList;
    }

}
