package Category.Service;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.Reader;
import java.util.List;

public interface CategoryService {
    
    List<Category> displayAllCategories();
    
    String addCategoriesToStory(List<Category> categories, Story story);

    List<Category> topCategoriesForTheMonth();
    
    List<Category> getPreferredCategories(Reader reader);

}