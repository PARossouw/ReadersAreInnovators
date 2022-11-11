package Category.Controller;

import Category.Model.Category;
import Category.Service.CategoryService;
import Story.Model.Story;
import java.util.Calendar;
import java.util.List;


public class CategoryControllerImpl implements CategoryController{
    
    CategoryService categoryService;

    @Override
    public List<Category> displayAllCategories() {
        return categoryService.displayAllCategories();
    }

    @Override
    public String addCategoriesToStory(List<Category> categories, Story story) {
        return categoryService.addCategoriesToStory(categories, story);
    }

    @Override
    public List<Category> topCategoriesForMonth() {
        return categoryService.topCategoriesForTheMonth();
    }

}