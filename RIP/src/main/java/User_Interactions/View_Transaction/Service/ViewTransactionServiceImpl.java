package User_Interactions.View_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.View_Transaction.Dao.ViewTransactionRepo;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
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
            return viewRepo.getAllStoryViewsInPeriod(startDate, endDate);
        } catch (SQLException ex) {
            Logger.getLogger(ViewTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

//        //hardcoding
//        HashMap<String, Integer> hardCodeMap = new HashMap();
//        
//        Story s1 = new Story(); s1.setTitle("69420 1st story ");
//        Story s2 = new Story(); s2.setTitle("2nd story " + startDate);
//        Story s3 = new Story(); s3.setTitle("3rd story ");
//        
//        int a = 5;
//        int b = 10;
//        int c = 15;
//        
//        hardCodeMap.put(s1.getTitle(), a);
//        hardCodeMap.put(s2.getTitle(), b);
//        hardCodeMap.put(s3.getTitle(), c);
////        
//        
//        
//        return hardCodeMap;
        
        

        
          //return Response.status(Response.Status.OK).entity(hardCodeMap).build();
    }
}
