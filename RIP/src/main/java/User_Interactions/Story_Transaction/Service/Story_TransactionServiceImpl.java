package User_Interactions.Story_Transaction.Service;

import SMS.smsreq;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Dao.UserRepo;
import User.Model.Editor;
import User.Model.User;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Dao.StoryTransactionRepo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Story_TransactionServiceImpl implements Story_TransactionService {

    private final StoryRepo storyRepo;
    private final StoryTransactionRepo storyTransactionRepo;
    private final UserRepo userRepo;

    public Story_TransactionServiceImpl(StoryRepo storyRepo, StoryTransactionRepo storyTransactionRepo, UserRepo userRepo) {
        this.storyTransactionRepo = storyTransactionRepo;
        this.storyRepo = storyRepo;
        this.userRepo = userRepo;
    }

    @Override
    public String approvePendingStory(Editor editor, Story story) {

        story.setIsApproved(true);

        smsreq sms = new smsreq();
        StringWriter sw = new StringWriter();
        try {
            if (storyTransactionRepo.createEvent(story, editor, "Approved Pending Story")) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd,hh:mm:ss");

                //getting the writer so we can get their number
                User user = new User();
                user.setUserID(Integer.parseInt(story.getWriter()));
                user = userRepo.getUserByUserID(user);

                //building the sms
                sms.setDatetime(sdf.format(date));
//                sms.setMsisdn(user.getPhoneNumber());
                sms.setMsisdn(user.getPhoneNumber());//this was hardcoded, check if it doesnt work
                sms.setPass("2group");
                sms.setUser("GROUP2");
                sms.setMessage("Story with the title: " + story.getTitle() + " has been approved and is now available for public view");

                //building a string with the structure of an xml document
                JAXBContext jaxBContext = JAXBContext.newInstance(smsreq.class);

                Marshaller marshaller = jaxBContext.createMarshaller();

                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

                marshaller.marshal(sms, sw);
            }
        } catch (SQLException | JAXBException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }

    @Override
    public String rejectPendingStory(Editor editor, Story story) {
        story.setIsApproved(false);
        
        smsreq sms = new smsreq();
        StringWriter sw = new StringWriter();
        try {
            if (storyTransactionRepo.createEvent(story, editor, "Reject Pending Story")) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd,hh:mm:ss");

                //getting the writer so we can get their number
                User user = new User();
                user.setUserID(Integer.parseInt(story.getWriter()));
                user = userRepo.getUserByUserID(user);

                //building the sms
                sms.setDatetime(sdf.format(date));
//                sms.setMsisdn(user.getPhoneNumber());
                sms.setMsisdn(user.getPhoneNumber());//this was hardcoded, check if it doesnt work
                sms.setPass("2group");
                sms.setUser("GROUP2");
                sms.setMessage("Story with the title: " + story.getTitle() + " has been rejected");

                //building a string with the structure of an xml document
                JAXBContext jaxBContext = JAXBContext.newInstance(smsreq.class);

                Marshaller marshaller = jaxBContext.createMarshaller();

                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

                marshaller.marshal(sms, sw);
            }
        } catch (SQLException | JAXBException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }

    @Override
    public String removeStoryByWriter(Writer writer, Story story) {
        try {
            List<Story> stories = storyRepo.getPendingStories();
            for (Story s : stories) {
                if (s.equals(story)) {
                    return storyTransactionRepo.createEvent(story, writer, "Removed Writers Story") ? "Story has been removed" : "Story could not be removed, please try again";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Unsuccessful operation";
        }
        return "Could not find story";
    }

}
