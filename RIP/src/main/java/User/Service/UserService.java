package User.Service;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.User;
import User.Model.Writer;
import java.util.List;


public interface UserService {
    
    User login(User user);
    
    Boolean addPreferredCategoriesToUser(List<Category> categories);
    
    Boolean registerUser(User user);
    
    Boolean blockWriter(Writer writer);
    
    Boolean addNewEditor(Editor Editor);
    
    Boolean removeEditor(Editor Editor);
    

}