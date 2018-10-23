package best.anastasia.cinemanearby.concepts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import best.anastasia.cinemanearby.db.contacts.PeriodContract;

@DatabaseTable(tableName = PeriodContract.TABLE_NAME)
public class Period {
    @DatabaseField(generatedId = true, columnName = PeriodContract.ID)
    private int id;
    @DatabaseField(columnName = PeriodContract.CLOSE)
    private boolean close;
    @DatabaseField(columnName = PeriodContract.DAY)
    private int day;
    @DatabaseField(columnName = PeriodContract.TIME)
    private String time;
    @DatabaseField(columnName = PeriodContract.PLACE_ID)
    private String placeId;

    public Period() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
