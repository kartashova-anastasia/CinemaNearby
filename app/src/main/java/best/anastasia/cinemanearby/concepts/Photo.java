package best.anastasia.cinemanearby.concepts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import best.anastasia.cinemanearby.db.contacts.PhotoContract;

@DatabaseTable(tableName = PhotoContract.TABLE_NAME)
public class Photo {
    @DatabaseField(generatedId = true, columnName = PhotoContract.ID)
    private int id;
    @DatabaseField(columnName = PhotoContract.LINK)
    private String link;
    @DatabaseField(columnName = PhotoContract.PLACE_ID)
    private String placeId;

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}