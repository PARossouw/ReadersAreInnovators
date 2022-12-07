package Category.Controller;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import java.util.List;

public interface CategoryController {

    List<Category> displayAllCategories();

    String addCategoriesToStory(List<Category> categories, Story story);

    List<Category> topCategoriesForMonth();

    List<Category> getPreferredCategories(User reader);

}
