package axelbremer.axelbremerpset3;

/**
 * Created by axel on 13-11-17.
 */

public class Dish {
    String name;
    String category;
    String description;
    String url;
    int id;
    int price;

    public Dish(String aName, String aCategory, String aDescription, String anUrl, int anId, int aPrice) {
        name = aName;
        category = aCategory;
        description = aDescription;
        url = anUrl;
        id = anId;
        price = aPrice;
    }
}
