package User_Interactions.View_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import User_Interactions.View_Transaction.Model.ViewTransaction;
import java.sql.SQLException;

public class ViewTransactionRepoImpl extends JDBCConfig implements ViewTransactionRepo {

    @Override
    public boolean createView(ViewTransaction view) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("insert into view_transaction (reader, story) values (?,?");
            ps.setInt(1, view.getReader().getUserID());
            ps.setInt(2, view.getStory().getStoryID());
            rowsAffected = ps.executeUpdate();
        }
        return rowsAffected == 1;
    }

}