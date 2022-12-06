package Category.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import DBManager.DBManager;
import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryServiceImpl extends DBManager implements CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> displayAllCategories() {

        List<Category> allCategories = new ArrayList<>();
        try {
            allCategories = categoryRepo.getAllCategories();
            return allCategories;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allCategories;
    }

    @Override
    public String addCategoriesToStory(List<Category> categories, Story story) {
        try {
            if (categories.isEmpty()) {
                return "No categories provided. Provide categories to add to story";
            }
            return categoryRepo.addCategoriesToStory(story, categories) ? "Categories successfully added to Story" : "Unable to add Categories";
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Could not complete the request at the moment, please try again later.";
    }

    @Override
    public HashMap<String, Integer> topCategoriesForTheMonth(String month) {

        HashMap<String, Integer> categoryList = new HashMap<>();
        try {
            categoryList = categoryRepo.topCategoriesForMonth(month);
            
            if(categoryList.isEmpty() || categoryList == null){
                categoryList.put("no data for selected period", -1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryList;
    }

    @Override
    public List<Category> getPreferredCategories(User reader) {
        List<Category> preferredCategories = new ArrayList<>();
        try {
            preferredCategories = categoryRepo.getPreferredCategories(reader);
            return preferredCategories;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferredCategories;
    }

}
