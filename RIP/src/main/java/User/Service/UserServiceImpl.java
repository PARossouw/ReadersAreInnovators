package User.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Dao.UserRepo;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final StoryRepo storyRepo;

    public UserServiceImpl(UserRepo userRepo, CategoryRepo categoryRepo, StoryRepo storyRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.storyRepo = storyRepo;
    }

    @Override
    public User login(User user) {

        User currentUser = null;

        try {
            //this should check if the user password equals the password
            currentUser = userRepo.getUser(user);

            if (currentUser != null) {
                if (currentUser.getPassword().equals(user.getPassword()) && (currentUser.getUsername().equals(user.getUsername())
                        || currentUser.getEmail().equals(user.getEmail()))) {

                    if (currentUser instanceof Reader) {
                        ((Reader) currentUser).setPreferredCategories(categoryRepo.getPreferredCategories(currentUser));
                        ((Reader) currentUser).setLikedStories(storyRepo.getLikedStories(currentUser));

                        //need to populate all writer stories if the user is a writer
                        if (currentUser instanceof Writer) {
                            List<Story> writerStories = storyRepo.getWriterStories((Writer) currentUser);
                            ((Writer) currentUser).setAllWriterStories(writerStories);
                            return currentUser;
                        }
                    }
                    return currentUser;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            currentUser = null;
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentUser;
    }

    @Override
    public String addPreferredCategoriesToUser(Reader reader, List<Category> categories) {

        try {
            if (reader == null || categories == null) {
                return "Something went wrong, please try again.";
            }
            return categoryRepo.addPreferredCategories(reader, categories) ? "Successfully added categories." : "Could not add categories at this time.";

        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Operation unsuccessful, please try again later.";
    }

    @Override
    public String registerUser(User user) {

        try {
            if (userRepo.getUser(user) != null) {
                return "This username or email is already in use.";
            } else {

                // return userRepo.createUser(user) ? "User registered successfully." : "Could not complete registration at this time.";
                return "Registration was successful. Please log in above. ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Operation unsuccessful, please try again later.";
    }

    public String blockWriter(Writer writer) {

        try {
            if (userRepo.getUser(writer) == null) {
                return "No such user exists.";
            } else if (writer.getRoleID() != 2) {
                return "This user is not a writer.";
            } else {
                return userRepo.blockWriter(writer) ? "Writer status removed." : "Could not removed writer status from this account at this time.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "unsuccessful";
    }

    @Override
    public String addNewEditor(Editor editor) {
        try {
            if (userRepo.getUser(editor) != null) {
                return "The username or email is already in use.";
            } else {
                return userRepo.createUser(editor) ? "Editor created successfully." : "Could not complete creation of a new editor at this time.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Operation unsuccessful, please try again later.";
    }

    @Override
    public String removeEditor(Editor editor) {
        try {
            if (userRepo.getUser(editor) == null) {
                return "This editor does not exist.";
            } else {
                return userRepo.deleteUser(editor) ? "Editor deleted successfully." : "Could not delete editor at this time.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Operation unsuccessful, please try again later.";
    }

    @Override
    public Map<Writer, Integer> topWriters() {
        Map<Writer, Integer> topWriters = new HashMap<>();

        try {
            topWriters = userRepo.topWriters();
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topWriters;
    }

    @Override
    public Map<Writer, Integer> topRejectedWritersForMonth() {
        Map<Writer, Integer> topRejectedWriters = new HashMap<>();

        try {
            userRepo.topRejectedWritersForMonth();
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topRejectedWriters;
    }

    @Override
    public Map<Writer, Integer> topApprovingEditors() {
        Map<Writer, Integer> topApprovingEditors = new HashMap<>();

        try {
            userRepo.topApprovingEditors();
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topApprovingEditors;
    }

    @Override
    public List<Writer> writerSearch(String writerSearch) {

        List<Writer> writers = new ArrayList<>();
        try {
            writers = userRepo.writerSearch(writerSearch);
            return writers;
        }
        catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writers;
    }
}
