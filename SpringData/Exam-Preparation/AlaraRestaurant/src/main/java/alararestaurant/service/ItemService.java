package alararestaurant.service;

import alararestaurant.domain.entities.Item;

import java.io.IOException;

public interface ItemService {

    Boolean itemsAreImported();

    String readItemsJsonFile() throws IOException;

    String importItems(String items);

    Item findItemByName(String name);
}
