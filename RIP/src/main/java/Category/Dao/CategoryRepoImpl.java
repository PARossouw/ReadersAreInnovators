package Category.Dao;

import Category.Model.Category;
import DBManager.DBManager;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import User.Model.Reader;

public class CategoryRepoImpl extends DBManager implements CategoryRepo {

    @Override
    public Boolean createCategory(Category category) throws SQLException {

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into Category (Category) values(?)");
            ps.setString(1, category.getName());
            rowsAffected = ps.executeUpdate();
        }
        close();
        return rowsAffected == 1;
    }

    @Override
    public Category retrieveCategory(Category category) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select Category, dateAdded from Category where categoryID = ?");
            ps.setInt(1, category.getCategoryID());
            rs = ps.executeQuery();

            if (rs.next()) {
                category.setName(rs.getString("Category"));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("dateAdded"));
                category.setDateAdded(calendar);

            }
        }
        close();
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
        close();
        return rowsAffected == 1;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {

        List<Category> categoryList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select categoryid, category, dateAdded from category");
            rs = ps.executeQuery();

            while (rs.next()) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("dateAdded"));

                categoryList.add(new Category(rs.getInt("categoryid"), rs.getString("category"), calendar));

            }
         
          
        }
            //-----Testing code------
         List<Category> categoryListTest = new ArrayList<>();
            Category cat1 = new Category();
            Category cat2 = new Category();
            Category cat3 = new Category();
            
            cat1.setName("Thriller");
            cat2.setName("Science");
            cat3.setName("Religious");
            
            
            
            
            
            
            categoryListTest.add(cat1);
            categoryListTest.add(cat2);
            categoryListTest.add(cat3);
            // --------------------
            
        close();
        return categoryListTest;
    }

    @Override
    public List<Category> getPreferredCategories(Reader reader) throws SQLException {

        List<Category> categoryList = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select categoryID, category, dateAdded from Category where CategoryID IN (select category from user_category where user = ?)");
            ps.setInt(1, reader.getUserID());
            rs = ps.executeQuery();

            while (rs.next()) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("dateAdded"));

                categoryList.add(new Category(rs.getInt("categoryID"), rs.getString("category"), calendar));

            }

        }
        close();
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
        close();
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

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("dateAdded"));

                categories.add(new Category(rs.getInt("categoryID"), rs.getString("category"), calendar));
            }
        }
        close();
        return categories;
    }

    @Override
    public List<Category> topCategoriesForMonth() throws SQLException { //DISCUSS WITH GROUP

        List<Category> topCategories = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select categoryID, c.category, dateAdded, count(vt.story) as categoryViews from category c "
                    + "inner join story_category sc on c.categoryID = sc.category "
                    + "inner join story s on sc.story = s.storyID "
                    + "inner join view_transaction vt on s.storyID = vt.story "
                    + "where month(dateViewed) = month(current_timestamp) and year(dateViewed) = year(current_timestamp) "
                    + "group by c.category order by categoryViews desc limit 5");
            rs = ps.executeQuery();

            while (rs.next()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("dateAdded"));

                topCategories.add(new Category(rs.getInt("categoryID"), rs.getString("category"), calendar));
            }
        }
        close();
        
        
        return topCategories;
    }

    @Override
    public Boolean addCategoriesToStory(Story story, List<Category> categories) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("insert into story_category (story, category) values (?,?)");
            for (Category category : categories) {
                ps.setInt(1, story.getStoryID());
                ps.setInt(2, category.getCategoryID());
                ps.addBatch();
            }
            rowsAffected = ps.executeBatch().length;
        }
        close();
        return rowsAffected == categories.size();
    }
}
