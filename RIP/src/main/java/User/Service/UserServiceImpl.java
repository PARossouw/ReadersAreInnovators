package User.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import User.Dao.UserRepo;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private CategoryRepo categoryRepo;

    @Override
    public User login(User user) {

        try {
            User userloggingIn = userRepo.getUser(user);

            if (userloggingIn != null) {
                return userloggingIn;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public String addPreferredCategoriesToUser(Reader reader, List<Category> categories) {

        try {
            if (categoryRepo.addPreferredCategories(reader, categories)) {
                return "successfully added categories";
            } else {
                return "unsuccessful operation";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "unsuccessful operation";
        }
    }

    @Override
    public String registerUser(User user) {

        try {
            if (userRepo.getUser(user) != null) {
                return "This username or email is already in use.";
            } else {
                return userRepo.createUser(user) ? "User registered successfully." : "Could not complete registration at this time.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Opertaion unsuccessful, please try again later.";
        }
    }

    @Override
    public String blockWriter(Writer writer) {
    
        try {
            if (userRepo.getUser(writer) == null) {
                return "No such user exists.";
            } else if (userRepo.getUser(writer).getRoleID() != 2) {
                return "This user is not a writer.";
            } else {
                return userRepo.blockWriter(writer) ? "Writer status removed." : "Could not removed writer status from this account at this time.";
            }
    }

    @Override
    public String addNewEditor(Editor editor) {
        try {
            User u = null;
            if (editor instanceof User) {
                u = (User) editor;
            }
            if (userRepo.getUser(u) != null) {
                return "This username or email is already in use.";
            } else {
                return userRepo.createUser(editor) ? "Editor created successfully." : "Could not complete creation of a new editor at this time.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Opertaion unsuccessful, please try again later.";
        }
    }

    @Override
    public String removeEditor(Editor editor) {
        try {
            User u = null;
            if (editor instanceof User) {
                u = (User) editor;
            }
            if (userRepo.getUser(u) == null) {
                return "This editor does not exist.";
            } else {
                return userRepo.deleteUser(editor) ? "Editor deleted successfully." : "Could not delete editor at this time.";

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Opertaion unsuccessful, please try again later.";
        }

}
