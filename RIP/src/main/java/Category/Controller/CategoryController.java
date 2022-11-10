package Category.Controller;

import Category.Model.Category;
import Story.Model.Story;
import java.util.Calendar;
import java.util.List;


public interface CategoryController {
    
    List<Category> displayAllCategories();
    
    String addCategoryToStory(Category category, Story story);
    
    List<Category> topCategpriesForMonth(Calendar calendar);
    
    

}