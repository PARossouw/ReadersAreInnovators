package User_Interactions.View_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.View_Transaction.Dao.ViewTransactionRepo;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewTransactionServiceImpl implements ViewTransactionService {

    private final ViewTransactionRepo viewRepo;

    public ViewTransactionServiceImpl(ViewTransactionRepo viewRepo) {
        this.viewRepo = viewRepo;
    }

    @Override
    public String viewStory(Story story, Reader reader) {
        try {
            return viewRepo.createView(story, reader) ? "View entry made." : "Could not record that the story was viewed.";
        } catch (SQLException ex) {
            Logger.getLogger(ViewTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Operation unsuccessful, please try again later.";
        }
    }

    @Override
    public Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) {
        try {
            return viewRepo.getAllStoryViewsInPeriod(startDate, endDate);
        } catch (SQLException ex) {
            Logger.getLogger(ViewTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
