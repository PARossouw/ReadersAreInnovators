package User_Interactions.Story_Transaction.Service;

import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.Editor;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Dao.StoryTransactionRepo;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Story_TransactionServiceImpl implements Story_TransactionService {

    private final StoryRepo storyRepo;
    private final StoryTransactionRepo storyTransactionRepo;

    public Story_TransactionServiceImpl(StoryRepo storyRepo, StoryTransactionRepo storyTransactionRepo) {
        this.storyTransactionRepo = storyTransactionRepo;
        this.storyRepo = storyRepo;
    }
    
    

    @Override
    public String approvePendingStory(Editor editor, Story story) {

        try {
            if (storyTransactionRepo.createEvent(story, editor, "Approved Pending Story")) {
                return "Story is approved";
            } else {
                return "unsuccessful operation";
            }
//        try {
//            //        try {
////            List<Story> stories = storyRepo.getPendingStories();

////            for (Story s : stories) {
////                if (s.equals(story)) {
////                    return storyTransactionRepo.createEvent(story, editor, "Approved Pending Story") ? "Story is approved" : "Story could not be approved, please try again";
//
////                }
////            }
////        } catch (SQLException ex) {
////            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
////            return "unsuccessful operation";
////        }
////        return "Something went wrong";
////return "Successz!";
//           // return storyTransactionRepo.createEvent(story, editor, "Approved Pending Story") ? "Story is approved" : "Story could not be approved, please try again";
//        
//        } catch (SQLException ex) {
//            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (SQLException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fuck";
    }

    @Override
    public String rejectPendingStory(Editor editor, Story story) {
        try {
            List<Story> stories = storyRepo.getPendingStories();
            for (Story s : stories) {
                if (s.equals(story)) {
                    return storyTransactionRepo.createEvent(story, editor, "Rejected Pending Story") ? "Story is rejected" : "Story could not be rejected, please try again";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Story_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "unsuccessful operation";
        }
        return "Could not find story";
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
