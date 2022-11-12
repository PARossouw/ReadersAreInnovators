package User.Controller;

import Category.Model.Category;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import User.Service.UserService;
import java.util.Calendar;
import java.util.List;


public class UserControllerImpl implements UserController{

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Writer> topRejectedWritersForMonth(Calendar calendar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Editor> topApprovingEditors() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String BlockWriter(Writer writer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

}