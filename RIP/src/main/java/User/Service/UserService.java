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

    String registerUser(User user);

    String blockWriter(Writer writer);

    String addNewEditor(Editor editor);

    String removeEditor(Editor editor);

    Map<Writer, Integer> topWriters() ;

    Map<Writer, Integer> topRejectedWritersForMonth();

    Map<Writer, Integer> topApprovingEditors();

    List<Writer> writerSearch(String writerSearch);
}
