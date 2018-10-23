package best.anastasia.cinemanearby.concepts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import best.anastasia.cinemanearby.db.contacts.FavoriteContract;

@DatabaseTable(tableName = FavoriteContract.TABLE_NAME)
public class Favorite {
    @DatabaseField(generatedId = true, columnName = FavoriteContract.ID)
    private int id;
    @DatabaseField(columnName = FavoriteContract.FAVORITE)
    private boolean favorite;
    @DatabaseField(columnName = FavoriteContract.PLACE_ID)
    private String placeId;

    public Favorite() {
    }

    public Favorite(String placeId, boolean favorite) {
        this.placeId = placeId;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}