package Story.Model;

import Category.Model.Category;
import java.util.Calendar;
import java.util.List;

public class Story {

    private Integer storyID;
    private String title;
    private String writer;
    private String description;
    private String imagePath;
    private String body;
    private Boolean isDraft;
    private Boolean isActive;
    private Calendar createdOn;
    private Boolean allowComments;
    private Boolean isApproved;
    private Integer views;
    private Integer likes;
    private Double avgRating;
    private List<Category> categoryList;

    public Story() {
    }

    public Story(Integer storyID, String title, String writer, String description, String imagePath, String body, Boolean isDraft, Boolean isActive, Calendar createdOn, Boolean allowComments, Boolean isApproved, Integer views, Integer likes, Double avgRating) {
        this.storyID = storyID;
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.imagePath = imagePath;
        this.body = body;
        this.isDraft = isDraft;
        this.isActive = isActive;
        this.createdOn = createdOn;
        this.allowComments = allowComments;
        this.isApproved = isApproved;
        this.views = views;
        this.likes = likes;
        this.avgRating = avgRating;
    }

    public Story(Integer storyID, String title, String writer, String description, String imagePath, String body, Boolean isDraft, Boolean isActive, Calendar createdOn, Boolean allowComments, Boolean isApproved, Integer views, Integer likes, Double avgRating, List<Category> categoryList) {
        this.storyID = storyID;
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.imagePath = imagePath;
        this.body = body;
        this.isDraft = isDraft;
        this.isActive = isActive;
        this.createdOn = createdOn;
        this.allowComments = allowComments;
        this.isApproved = isApproved;
        this.views = views;
        this.likes = likes;
        this.avgRating = avgRating;
        this.categoryList = categoryList;
    }

    public Integer getStoryID() {
        return storyID;
    }

    public void setStoryID(Integer storyID) {
        this.storyID = storyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Calendar getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Calendar createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(Boolean allowComments) {
        this.allowComments = allowComments;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "Story{" + "storyID=" + storyID + ", title=" + title + ", writer=" + writer + ", description=" + description + ", imagePath=" + imagePath + ", body=" + body + ", isDraft=" + isDraft + ", isActive=" + isActive + ", createdOn=" + createdOn + ", allowComments=" + allowComments + ", isApproved=" + isApproved + ", views=" + views + ", likes=" + likes + ", avgRating=" + avgRating + '}';
    }

}
