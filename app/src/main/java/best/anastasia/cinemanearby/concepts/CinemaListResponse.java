package best.anastasia.cinemanearby.concepts;

import java.util.List;

public class CinemaListResponse {
    private String token;
    private List<Cinema> cinemaList;

    public CinemaListResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Cinema> getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }
}