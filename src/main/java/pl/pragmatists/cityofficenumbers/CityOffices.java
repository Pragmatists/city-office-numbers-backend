package pl.pragmatists.cityofficenumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityOffices {

    private final FavoriteOffices favoriteOffices;

    @Autowired
    public CityOffices(FavoriteOffices favoriteOffices) {
        this.favoriteOffices = favoriteOffices;
    }

    public List<CityOffice> getForUser(final String userId) {
        List<CityOffice> cityOffices = allOffices();
        cityOffices.stream().forEach(o -> o.favorite = favoriteOffices.isFavorite(new UserId(userId), new OfficeId(o.id)));
        return cityOffices;
    }

    public List<CityOffice> allOffices() {
        return Arrays.asList(
                new CityOffice("USC Andersa").id("5d2e698a-9c31-456b-8452-7ce33e7deb94"),
                new CityOffice("USC Falęcka").id("ef5df1a7-882e-4cc5-815b-78768e985724"),
                new CityOffice("UD Białołęka").id("95fee469-79db-4b4b-9ddc-91d49d1f0f51"),
                new CityOffice("UD Bielany").id("9c3d5770-57d8-4365-994c-69c5ac4186ee"),
                new CityOffice("UD Ochota").id("624d7e2a-bf45-48d6-ba79-8b512e662d1c"),
                new CityOffice("UD Wola").id("7ef70889-4eb9-4301-a970-92287db23052"),
                new CityOffice("UD Żoliborz").id("831ef31a-b2a3-4cbb-aaa5-cb90fe05ad8c")
        );
    }
}
