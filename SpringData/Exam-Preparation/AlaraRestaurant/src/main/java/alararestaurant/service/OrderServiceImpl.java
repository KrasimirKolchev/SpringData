package alararestaurant.service;

import alararestaurant.domain.dtos.OrderSeedRootDTO;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.domain.entities.OrderType;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final EmployeeService employeeService;
    private final ItemService itemService;
    private final OrderItemRepository orderItemRepository;

    private final DateTimeFormatter FORMAT_DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validationUtil, EmployeeService employeeService, ItemService itemService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.employeeService = employeeService;
        this.itemService = itemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Boolean ordersAreImported() {
       return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/orders.xml");
    }

    @Override
    public String importOrders() throws JAXBException, FileNotFoundException {
        OrderSeedRootDTO orders = this.xmlParser
                .importFromXML(OrderSeedRootDTO.class, "src/main/resources/files/orders.xml");
        StringBuilder sb = new StringBuilder();

        orders.getOrders().forEach(o -> {
            if (this.validationUtil.isValid(o)) {
                if (this.employeeService.findEmployeeByName(o.getEmployee()) != null) {
                    List<OrderItem> items = new ArrayList<>();

                    o.getItems().getItems().forEach(i -> {
                        OrderItem orderItem = new OrderItem();

                        if (this.itemService.findItemByName(i.getName()) != null) {
                            Item item = this.itemService.findItemByName(i.getName());
                            orderItem.setItem(item);
                            orderItem.setQuantity(i.getQuantity());
                        } else {
                            orderItem = null;
                        }

                        items.add(orderItem);
                    });

                    if (!items.contains(null)) {
                        Order order = this.modelMapper.map(o, Order.class);
                        order.setEmployee(this.employeeService.findEmployeeByName(o.getEmployee()));
                        order.setDateTime(LocalDateTime.parse(o.getDateTime(), FORMAT_DATE_TIME));
                        order.setType(OrderType.valueOf(o.getType().toUpperCase()));
                        order.setOrderItems(items);
                        this.orderRepository.saveAndFlush(order);
                        Order ord = this.orderRepository.getOne(this.orderRepository.count());

                        List<OrderItem> items1 = items.stream().peek(i -> i.setOrder(ord)).collect(Collectors.toList());
                        this.orderItemRepository.saveAll(items1);
                    } else {
                        sb.append("Invalid data format.");
                    }
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
    public String exportOrdersFinishedByTheBurgerFlippers() {
        List<Order> orders = this.orderRepository
                .findOrdersByEmployeePositionNameOrderByEmployeeNameAscIdAsc("Burger Flipper");

        StringBuilder sb = new StringBuilder();
        orders.forEach(o -> {
            sb.append(String.format("\nName: %s\nOrders:\n", o.getEmployee().getName()));
            sb.append(String.format("   Customer: %s\n   Items:", o.getCustomer()));
            o.getOrderItems().forEach(i -> sb.append(String.format("\n\tName: %s\n\tPrice: %s\n\tQuantity: %d\n"
                    , i.getItem().getName(), i.getItem().getPrice(), i.getQuantity())));
        });

        return sb.toString();
    }
}
