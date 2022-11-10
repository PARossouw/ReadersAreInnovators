package Category.Service;

import Category.Model.Category;
import Story.Model.Story;
import java.util.List;


public interface CategoryServiceRepo {
    
    List<Category> displayAllCategories();
    
    String addCategoryToStory(Category category, Story story);

}