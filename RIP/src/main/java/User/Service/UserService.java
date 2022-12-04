package User.Service;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import java.util.List;
import java.util.Map;

public interface UserService {

    User login(User user);

    String addPreferredCategoriesToUser(Reader reader, List<Category> categories);
    
String addPreferredCategoriesToNewUser(Reader reader);
    
    String registerUser(User user);

    String blockWriter(Writer writer);

    String addNewEditor(Editor editor);

    String removeEditor(Editor editor);

    Map<String, Integer> topWriters() ;

    Map<String, Integer> topRejectedWritersForMonth();

    Map<String, Integer> topApprovingEditors();

    List<Writer> writerSearch(String writerSearch);
}
