package User_Interactions.View_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.View_Transaction.Dao.ViewTransactionRepo;
import java.sql.SQLException;
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
    public Map<String, Integer> getAllStoryViewsInPeriod(String startDate, String endDate) {
        try {

            Map<String, Integer> storyViewsMap = new HashMap<>();

            storyViewsMap = viewRepo.getAllStoryViewsInPeriod(startDate, endDate);

            if (storyViewsMap.isEmpty() || storyViewsMap == null) {
                storyViewsMap.put("no data for selected period", -1);
                return storyViewsMap;
            }
            return storyViewsMap;

        } catch (SQLException ex) {
            Logger.getLogger(ViewTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
