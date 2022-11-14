package User.Controller;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import User.Service.UserService;
import java.util.Calendar;
import java.util.List;

public class UserControllerImpl implements UserController {

    private UserService userService;

    private UserService userService;
    
    @Override
    public User login(User user) 
    {
    return userService.login(user);
    }

    @Override
    public String addPreferredCategriesToUser(Reader reader,List<Category> categories) 
    {
        return userService.addPreferredCategoriesToUser(reader,categories);
    }

    @Override
    public String registerUser(User user) 
    {
        return userService.registerUser(user);
    }
    
    @Override
    public String blockWriter(Writer writer) {
        return userService.blockWriter(writer);
    }

    @Override
    public String addNewEditor(Editor editor) {
        return userService.addNewEditor(editor);
    }

    @Override
    public String removeEditor(Editor editor) {
        return userService.removeEditor(editor);
    }

    @Override
    public List<Writer> topWriters() {
        List<Writer> writers = userService.TopWriters();
        if (writers.size() > 30) {
            for (int i = 30; i < writers.size(); i++) {
                writers.remove(i);
            }
        }
        return writers;
    }

    @Override
    public List<Writer> topRejectedWritersForMonth() {
        List<Writer> writers = userService.TopRejectedWritersForMonth();
        if (writers.size() > 5) {
            for (int i = 5; i < writers.size(); i++) {
                writers.remove(i);
            }
        }
        return writers;
    }

    @Override
    public List<Editor> topApprovingEditors() {
        List<Editor> editors = userService.topApprovingEditors();
        if (editors.size() > 3) {
            for (int i = 3; i < editors.size(); i++) {
                editors.remove(i);
            }
        }
        return editors;
    }

    @Override
    public String BlockWriter(Writer writer) {
        return userService.blockWriter(writer);
    }

}
