package pl.pragmatists.cityofficenumbers;

public class CityOffice {
    public final String name;
    public String id;
    public boolean favorite = false;

    public CityOffice(String name) {

        this.name = name;
    }

    public CityOffice id(String id) {
        this.id = id;
        return this;
    }
}
