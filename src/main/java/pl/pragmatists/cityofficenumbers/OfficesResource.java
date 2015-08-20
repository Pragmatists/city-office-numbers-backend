package pl.pragmatists.cityofficenumbers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@RestController
@RequestMapping("/users/{userId}/offices")
public class OfficesResource {

    @Autowired
    private CityOffices cityOffices;

    @Autowired
    private FavoriteOffices favoriteOffices;

    @RequestMapping(method = RequestMethod.GET)
    public List<CityOffice> get(@PathVariable("userId") String userId) {
        return cityOffices.getForUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{officeId}/favorite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void post(@PathVariable("userId") String userId, @PathVariable("officeId") String officeId, @RequestBody FavoriteJson json) {
        if (json.favorite) {
            favoriteOffices.addFavorite(new UserId(userId), new OfficeId(officeId));
        } else {
            favoriteOffices.removeFavorite(new UserId(userId), new OfficeId(officeId));
        }
    }

    public static class FavoriteJson {
        public boolean favorite;
    }

}
