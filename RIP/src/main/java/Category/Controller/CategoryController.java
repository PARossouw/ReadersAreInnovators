package Category.Controller;

import Category.Model.Category;
import Story.Model.Story;
import java.util.List;

public interface CategoryController {

    List<Category> displayAllCategories();

    String addCategoriesToStory(List<Category> categories, Story story);

    List<Category> topCategoriesForMonth();

}
