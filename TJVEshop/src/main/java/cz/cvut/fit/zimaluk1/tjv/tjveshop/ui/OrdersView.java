package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.OrderDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.ProductDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Route(value = "/orders", layout = MainLayout.class)
public class OrdersView extends HorizontalLayout {
    String id;
    CookieReader cook;
    NoIdNavigator navigator;
    Client client = ClientBuilder.newClient();
    List<OrderDto> fetchedOrders = new ArrayList<OrderDto>();
    List<ProductDto> fetchedProducts = new ArrayList<ProductDto>();

    Grid<OrderDto> orders = new Grid<>(OrderDto.class);

    Grid<ProductDto> products = new Grid<>(ProductDto.class);
    Button view = new Button("View");

    IntegerField inputField = new IntegerField();
    public OrdersView(CookieReader cooker, NoIdNavigator nav){
        cook = cooker;
        navigator = nav;
        id = cook.getCookie("id");
        setSizeFull();
        initInputField();
        initButton();
        initProductsGrid();
        HorizontalLayout input = initInput();
        VerticalLayout inputAndOrdersGrid = initIAOG(input);
        add(inputAndOrdersGrid);
    }

    private void initProductsGrid() {
        products.setColumns("name", "price", "amount");
    }

    private VerticalLayout initIAOG(HorizontalLayout input) {
        VerticalLayout iaog = new VerticalLayout();
        iaog.setHeightFull();
        iaog.setWidth("40%");
        initOrdersGrid();
        iaog.add(input, orders);
        return iaog;
    }

    private void initOrdersGrid() {
        orders.setColumns("state", "time");
        orders.setSizeFull();
        orders.setItems(fetchedOrders);
        showProducts();
    }

    /**
     * Functionality behind showing the products and their amount
     * After double-clicking an order in the grid
     */
    private void showProducts() {
        orders.addItemDoubleClickListener(ord ->
            {
                OrderDto clicked = ord.getItem();
                fetchProducts(clicked.getId());
                refreshAll();
                add(products);
            });

    }

    /**
     * Gets all the products belonging to an order including the amount(M:N)
     * @param orderId order id
     */
    private void fetchProducts(Long orderId) {
        navigator.handle(id, "Your ID is null", true);
        Response res = client.target("http://localhost:8080/orders/" + orderId + "/products")
                .request(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        fetchedProducts = res.readEntity(new GenericType<List<ProductDto>>(){});
    }

    private HorizontalLayout initInput() {
        HorizontalLayout input = new HorizontalLayout(inputField, view);
        input.setAlignItems(Alignment.BASELINE);
        input.setWidth("30%");
        return input;
    }

    private void initButton() {
        view.addClickListener(click -> {
            fetchOrders(inputField.getValue());
            refreshAll();
        });
    }

    private void refreshAll() {
        orders.setItems(fetchedOrders);
        products.setItems(fetchedProducts);
    }

    private void initInputField() {
        Long maxOrders = getAmount();
        inputField.setLabel("Amount of Orders");
        inputField.setHelperText("Max " + maxOrders + " orders");
        inputField.setMin(0);
        inputField.setMax(maxOrders.intValue());
        inputField.setValue(maxOrders.intValue());
        inputField.setHasControls(true);
        inputField.setSizeFull();
    }

    private Long getAmount() {
        fetchOrders(-1);
        return (long) fetchedOrders.size();
    }

    /**
     * Gets n orders that the customer has placed
     *
     * @param n , if n == -1 -> I Want All Orders
     */
    private void fetchOrders(int n) {
        navigator.handle(id, "Your ID is null", true);
        String path = new String("http://localhost:8080/customers/" + id + "/orders");
        Response res;
        if(n == -1){
            res = client.target(path).request(MediaType.APPLICATION_JSON_TYPE)
                    .get(Response.class);
        } else{
            res = client.target(path + "/" + n).request(MediaType.APPLICATION_JSON_TYPE)
                    .get(Response.class);
        }
        fetchedOrders = res.readEntity(new GenericType<List<OrderDto>>(){});
    }
}
