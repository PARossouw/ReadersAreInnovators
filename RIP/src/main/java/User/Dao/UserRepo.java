package User.Dao;

import Category.Model.Category;
import User.Model.User;
import java.util.List;

public interface UserRepo {

    Boolean createUser(User user);

    User getUser(User user);
    
    Boolean updateUser(User user);

    Boolean deleteUser(User user);
    
    List<Category> getPreferredCatesgories(User user);
    
    List<Story> getLikedStories(User user);
    
    
    
    
    
    

}
