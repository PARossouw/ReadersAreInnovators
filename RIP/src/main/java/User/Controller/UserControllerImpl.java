package User.Controller;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import User.Service.UserService;
import java.util.List;
import java.util.Map;

public class UserControllerImpl implements UserController {

    private UserService userService;

    @Override
    public User login(User user) {
        return userService.login(user);
    }

    @Override
    public String addPreferredCategriesToUser(Reader reader, List<Category> categories) {
        return userService.addPreferredCategoriesToUser(reader, categories);
    }

    @Override
    public String registerUser(User user) {
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
    public Map<Writer, Integer> topWriters() {
        Map<Writer, Integer> topWriters = userService.topWriters();
        return topWriters;
    }

    @Override
    public Map<Writer, Integer> topRejectedWritersForMonth() {
        Map<Writer, Integer> topRejectedWriters = userService.topRejectedWritersForMonth();
        return topRejectedWriters;
    }

    @Override
    public Map<Writer, Integer> topApprovingEditors() {
        Map<Writer, Integer> topApprovingEditors = userService.topApprovingEditors();
        return topApprovingEditors;
    }

    @Override
    public String BlockWriter(Writer writer) {
        return userService.blockWriter(writer);
    }

}
