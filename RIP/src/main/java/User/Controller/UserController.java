package User.Controller;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import java.util.Calendar;
import java.util.List;

public interface UserController {

    User login(User user);

    String addPreferredCategriesToUser(Reader reader,List<Category> categories);

    String registerUser(User user);

    String blockWriter(Writer writer);

    String addNewEditor(Editor editor);

    String removeEditor(Editor editor);

    List<Writer> topWriters();

    List<Writer> topRejectedWritersForMonth();

    List<Editor> topApprovingEditors();

    String BlockWriter(Writer writer);

}
