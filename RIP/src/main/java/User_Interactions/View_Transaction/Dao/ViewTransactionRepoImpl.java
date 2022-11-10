package User_Interactions.View_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ViewTransactionRepoImpl extends JDBCConfig implements ViewTransactionRepo {

    @Override
    public Boolean createView(Story story, Reader reader) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("insert into view_transaction (reader, story) values (?,?");
            ps.setInt(1, reader.getUserID());
            ps.setInt(2, story.getStoryID());
            rowsAffected = ps.executeUpdate();
        }
        return rowsAffected == 1;
    }

    @Override
    public Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) throws SQLException {
        
        Map<Story, Integer> allStoryViews = new HashMap<>();
        
        if (getConnection()!=null) {
            ps = getConnection().prepareStatement("select storyID, title, writer, count(vt.*) from story s "
                    + "inner join view_transaction vt on s.storyID = vt.story where vt.dateViewed between ? and ?");//Pieter - check with someone
            ps.setDate(1, (Date) startDate.getTime());
            ps.setDate(2, (Date) endDate.getTime());
            rs = ps.executeQuery();
        }
        return allStoryViews;
    }

}