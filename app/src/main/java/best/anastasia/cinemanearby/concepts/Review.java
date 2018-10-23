package best.anastasia.cinemanearby.concepts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import best.anastasia.cinemanearby.db.contacts.ReviewContract;

@DatabaseTable(tableName = ReviewContract.TABLE_NAME)
public class Review {
    @DatabaseField(generatedId = true, columnName = ReviewContract.ID)
    private int id;
    @DatabaseField(columnName = ReviewContract.AUTHOR)
    private String author;
    @DatabaseField(columnName = ReviewContract.AUTHOR_URL)
    private String authorUrl;
    @DatabaseField(columnName = ReviewContract.LANGUAGE)
    private String language;
    @DatabaseField(columnName = ReviewContract.PROFILE_PHOTO_URL)
    private String profilePhotoUrl;
    @DatabaseField(columnName = ReviewContract.RATING)
    private int rating;
    @DatabaseField(columnName = ReviewContract.TIME_DESC)
    private String timeDesc;
    @DatabaseField(columnName = ReviewContract.TEXT)
    private String text;
    @DatabaseField(columnName = ReviewContract.TIME)
    private int time;
    @DatabaseField(columnName = ReviewContract.PLACE_ID)
    private String placeId;

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
