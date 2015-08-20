package pl.pragmatists.cityofficenumbers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class FavoriteOffices {

    private HashMap<UserId, Set<OfficeId>> favorites = new HashMap<>();

    public void addFavorite(UserId userId, OfficeId officeId) {
        if (!favorites.containsKey(userId)) {
            favorites.put(userId, new LinkedHashSet<>());
        }
        favorites.get(userId).add(officeId);
    }

    public boolean isFavorite(UserId userId, OfficeId officeId) {
        return favorites.containsKey(userId) && favorites.get(userId).contains(officeId);
    }

    public void removeFavorite(UserId userId, OfficeId officeId) {
        if (!favorites.containsKey(userId))
            return;
        Set<OfficeId> offices = favorites.get(userId);
        offices.remove(officeId);
    }
}
