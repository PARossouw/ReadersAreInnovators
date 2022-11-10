package User.Service;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import java.util.List;

public interface UserService {

    User login(User user);

    String addPreferredCategoriesToUser(Reader reader, List<Category> categories);

    String registerUser(User user);

    String blockWriter(Writer writer);

    String addNewEditor(Editor Editor);

    String removeEditor(Editor Editor);

}
