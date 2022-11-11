package Category.Service;

import Category.Model.Category;
import Story.Model.Story;
import java.util.List;


public interface CategoryService {
    
    List<Category> displayAllCategories();
    
    String addCategoriesToStory(List<Category> categories, Story story);

    public List<Category> topCategoriesForTheMonth();

}