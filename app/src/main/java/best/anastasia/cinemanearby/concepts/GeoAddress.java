package best.anastasia.cinemanearby.concepts;

public class GeoAddress {

    private String street;
    private String house;
    private String city;

    public GeoAddress() {
    }

    public GeoAddress(String street, String house, String city) {
        this.street = street;
        this.house = house;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toShortAddress() {
        return street + ", " + house;
    }

    public String toFullAddress() {
        return city + ", " + toShortAddress();
    }
}