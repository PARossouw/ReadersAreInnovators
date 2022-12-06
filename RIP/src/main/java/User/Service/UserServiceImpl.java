package User.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import SMS.smsreq;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Dao.UserRepo;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Service.Story_TransactionServiceImpl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                if (currentUser.getPassword().equals(getMd5(user.getPassword()))
                        && (currentUser.getUsername().equals(user.getUsername())
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
    public String addPreferredCategoriesToNewUser(Reader reader) {
        try {
            categoryRepo.addPreferredCategoriesToUser(reader);
            return "Successfully added categories to user";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "Operation unsuccessful, please try again later";
    }

    @Override
    public String registerUser(User user) {

        try {
//            if (userRepo.getUser(user).getUsername().equals(user.getUsername())) {
//                return "This username or email is already in use.";
//            } else {

            // return userRepo.createUser(user) ? "User registered successfully." : "Could not complete registration at this time.";
            user.setPassword(getMd5(user.getPassword()));
            userRepo.createUser(user);
            return "Registration was successful. Please log in above.";
//            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Operation unsuccessful, please try again later." + user.toString();
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
    public Map<String, Integer> topWriters() {
        Map<String, Integer> topWriters = new HashMap<>();

        try {
            topWriters = userRepo.topWriters();
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topWriters;
    }

    @Override
    public Map<String, Integer> topRejectedWritersForMonth() {
        Map<String, Integer> topRejectedWriters = new HashMap<>();

        try {
            topRejectedWriters = userRepo.topRejectedWritersForMonth();
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topRejectedWriters;
    }

    @Override
    public Map<String, Integer> topApprovingEditors() {
        Map<String, Integer> topApprovingEditors = new HashMap<>();

        try {
            topApprovingEditors = userRepo.topApprovingEditors();
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
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writers;
    }

    @Override
    public String referFriend(User user, String number) {

       
smsreq sms = new smsreq();
        StringWriter sw = new StringWriter();
        try {
             
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd,hh:mm:ss");

                sms.setDatetime(sdf.format(date));

                sms.setMsisdn(number);
                sms.setPass("2group");
                sms.setUser("GROUP2");
                sms.setMessage("A friend by the username of " + user.getUsername() + " has referred you to read our story of the day: http://localhost:8080/BitByBitClient/StoryServlet");


            //building a string with the structure of an xml document
            JAXBContext jaxBContext = JAXBContext.newInstance(smsreq.class);

            Marshaller marshaller = jaxBContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            marshaller.marshal(sms, sw);


        } catch (JAXBException ex) {

            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }

    public String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
