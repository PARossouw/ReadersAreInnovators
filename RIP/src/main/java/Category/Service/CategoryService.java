package Category.Service;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.Reader;
import User.Model.User;
import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    
    List<Category> displayAllCategories();
    
    String addCategoriesToStory(List<Category> categories, Story story);

    HashMap<String, Integer> topCategoriesForTheMonth(String month);
    
    List<Category> getPreferredCategories(User reader);

}