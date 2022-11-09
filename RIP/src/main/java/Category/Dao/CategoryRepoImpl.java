package Category.Dao;

import Category.Model.Category;
import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import user.Reader.Model.Reader;

public class CategoryRepoImpl extends JDBCConfig implements CategoryRepo {
    
    public static void main(String[] args) throws SQLException {
        CategoryRepoImpl cri = new CategoryRepoImpl();
        
        cri.createCategory(new Category("Test category Anton"));
    }

    @Override
    public Boolean createCategory(Category category) throws SQLException {

        if (getConnection() != null) {//this null check okay?

            ps = getConnection().prepareStatement("insert into Category (Category) values(?)");
            ps.setString(1, category.getName());
            rowsAffected = ps.executeUpdate();

        }
        closeConnection();

        return rowsAffected == 1;
    }

    @Override
    public Category retrieveCategory(int CategoryID) throws SQLException {

        Category category = new Category();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select Category, dateAdded from Category where categoryID = ?");
            ps.setInt(1, CategoryID);

            rs = ps.executeQuery();

            if (rs.next()) {
                category.setCategoryID(CategoryID);
                category.setName(rs.getString("Category"));
                
                Date createdOn = rs.getDate("dateAdded");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);
                category.setDateAdded(calendar);
            
            }
        }
        closeConnection();
        return category;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("update table category set Category = ? where categoryID = ?");
            ps.setString(1, category.getName());
            ps.setInt(2, category.getCategoryID());

            rowsAffected = ps.executeUpdate();

        }
        closeConnection();
        return rowsAffected == 1;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {

        List<Category> categoryList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select categoryid, category, dateAdded from category");
            rs = ps.executeQuery();

            while (rs.next()) {

                Date createdOn = rs.getDate("dateAdded");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);

                categoryList.add(new Category(rs.getInt("categoryid"), rs.getString("category"), calendar));

            }
        }
        return categoryList;
    }

    @Override
    public List<Category> getPreferredCategories(Reader reader) throws SQLException {

        List<Category> categoryList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select categoryID, category, dateAdded from Category where CategoryID IN (select category from reader_category where readerID = ?)");
            ps.setInt(1, reader.getUserID());
            rs = ps.executeQuery();

            while (rs.next()) {
                
                 Date createdOn = rs.getDate("dateAdded");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);
                
                
                
                categoryList.add(new Category(rs.getInt("categoryID"), rs.getString("category"), calendar));

            }

            closeConnection();

        }
        return categoryList;
    }

    @Override
    public Boolean addPreferredCategories(Reader reader, List<Category> categories) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("insert into user_category (user, category) values(?, ?)");

            for (int i = 0; i < categories.size(); i++) {
                ps.setInt(reader.getUserID(), categories.get(i).getCategoryID());

            }

            rowsAffected = ps.executeBatch().length;

        }
        closeConnection();
        return rowsAffected == categories.size();
    }

    @Override
    public List<Category> getStoryCategories(Story story) throws SQLException {

        List<Category> categories = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select categoryid, category, dateAdded from Category where categoryid IN (select category from story_category where story = ?)");
            ps.setInt(1, story.getViews());
            rs = ps.executeQuery();

            while (rs.next()) {
                
                 Date createdOn = rs.getDate("dateAdded");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);
                
                
                categories.add(new Category(rs.getInt("categoryID"), rs.getString("category"), calendar));
            }
        }
        closeConnection();
        return categories;
    }

}
