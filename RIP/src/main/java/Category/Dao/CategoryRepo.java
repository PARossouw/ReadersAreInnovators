package Category.Dao;

import Category.Model.Category;


public interface CategoryRepo {
    
    Boolean createCategory(Category category);
    Category retrieveCategory(int CategoryID);
    Boolean updateCategory (Category category);
    
    
    

}