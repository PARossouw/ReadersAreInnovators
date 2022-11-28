package User_Interactions.Story_Transaction.Service;

import SMS.smsreq;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Dao.UserRepo;
import User.Model.Editor;
import User.Model.User;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Dao.StoryTransactionRepo;
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
    public smsreq approvePendingStory(Editor editor, Story story) {

        smsreq sms = null;
        try {
            if (storyTransactionRepo.createEvent(story, editor, "Approved Pending Story")) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd,hh:mm:ss");

                //getting the writer so we can get their number
                User user = new User();
                user.setUsername(story.getWriter());
                user.setPhoneNumber("0739068691");
                //user = userRepo.getUser(user); GOTTA TEST THIS ON A DATABASE

                //building the sms
                sms = new smsreq();
                sms.setDatetime(sdf.format(date));
                sms.setMsisdn(user.getPhoneNumber());
                sms.setMessage("Story with the title: \"" + story.getTitle() + "\" has been approved and is now available for public view");
                return sms;
            } else {
                return sms;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sms;
    }

    @Override
    public smsreq rejectPendingStory(Editor editor, Story story) {

                smsreq sms = null;
        try {
            if (storyTransactionRepo.createEvent(story, editor, "Rejected Pending Story")) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd,hh:mm:ss");

                //getting the writer so we can get their number
                User user = new User();
                user.setUsername(story.getWriter());
                user = userRepo.getUser(user);

                //building the sms
                sms = new smsreq();
                sms.setDatetime(sdf.format(date));
                sms.setMsisdn(user.getPhoneNumber());
                sms.setMessage("Story with the title: \"" + story.getTitle() + "\" has been rejected and is not available for public view");
                return sms;
            } else {
                return sms;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sms;
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
            return "unsuccessful operation";
        }
        return "Could not find story";
    }

}
