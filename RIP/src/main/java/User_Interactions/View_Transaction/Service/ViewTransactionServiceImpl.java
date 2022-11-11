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

    private ViewTransactionRepo viewRepo;

    @Override
    public String viewStory(Story story, Reader reader) {
        try {
            return viewRepo.createView(story, reader) ? "View entry made." : "Could not record that the story was viewed.";
        } catch (SQLException ex) {
            Logger.getLogger(ViewTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}