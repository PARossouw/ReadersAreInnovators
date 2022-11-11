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
        
        try {
            return categoryRepo.getAllCategories();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String addCategoriesToStory(List<Category> categories, Story story) {//I can't find the repo method to call over here
        
        if(categoryRepo.addCategoriesToStory(categories, story)){
            return "Successfully added";
        }
        else{
            return "Unsuccessful operation";
        }
    }

    @Override
    public List<Category> topCategoriesForTheMonth() {
        
        List<Category> categoryList = new ArrayList<>();
        categoryList = categoryRepo.topCategoriesForMonth();
        
        if(categoryList != null){
            return categoryList;
        }
        else{
            return null;
        }
    }

   

}