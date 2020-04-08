package alararestaurant.service;

import alararestaurant.domain.dtos.ItemSeedDTO;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;


    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryService categoryService, FileUtil fileUtil,
                           ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean itemsAreImported() {
     return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/items.json");
    }

    @Override
    public String importItems(String items) {
        ItemSeedDTO[] itemsDtos = this.gson.fromJson(items, ItemSeedDTO[].class);
        StringBuilder sb = new StringBuilder();

        Arrays.stream(itemsDtos).forEach(i -> {
            if (this.validationUtil.isValid(i)) {
                if (this.categoryService.findCategoryByName(i.getCategory()) == null) {
                    Category category = new Category();
                    category.setName(i.getCategory());
                    this.categoryService.register(category);
                }

                if (this.findItemByName(i.getName()) == null) {
                    Item item = this.modelMapper.map(i, Item.class);
                    item.setCategory(this.categoryService.findCategoryByName(i.getCategory()));
                    sb.append(String.format("Record %s successfully imported.", item.getName()));
                    this.itemRepository.saveAndFlush(item);
                } else {
                    sb.append("Invalid data format.");
                }
            } else {
                sb.append("Invalid data format.");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public Item findItemByName(String name) {
        return this.itemRepository.findItemByName(name);
    }
}
