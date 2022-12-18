package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.OrderDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.OrderProductDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.ProductDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "/products", layout = MainLayout.class)
public class ProductsView extends HorizontalLayout {
    String id;
    CookieReader cook;
    NoIdNavigator navigator;

    Map<Long,ProductDto> cart = new HashMap<>();
    List <ProductDto> fetchedProds = new ArrayList<ProductDto>();
    Grid<ProductDto> products = new Grid<>(ProductDto.class);
    Grid<ProductDto> cartGrid = new Grid<>(ProductDto.class);
    public ProductsView(CookieReader cooker, NoIdNavigator nav){
        setSizeFull();
        cook = cooker;
        navigator = nav;
        id = cook.getCookie("id");
        initGrid();
        initShoppingCart();
        refreshAll();
        addToCart();

    }

    private void initShoppingCart() {
        cartGrid.setSizeFull();
        cartGrid.setColumns("name", "price", "amount");
        cartGrid.addComponentColumn(prod -> {
            Button del = new Button(new Icon("lumo", "cross"));
            del.getElement().setAttribute("aria-label", "Close");
            //We want to decrement the amount of a Product by one
            //And return it to the Available Products grid
            del.addClickListener(e -> {
                Long id = prod.getId();
                prod.changeAmount(-1);
                //Remove it from the cart completely if the amount is now 0
                if(prod.getAmount() < 1){
                    cart.remove(id);
                }
                //Try to find the same product in our Available products Collection
                Optional <ProductDto> AvProd = fetchedProds.stream().filter(el -> el.getId().equals(id)).findFirst();
                //If it is present, I just increment its count, if not then I create it and its amount will be 1
                AvProd.ifPresentOrElse(productDto -> productDto.changeAmount(1), () -> fetchedProds.add(new ProductDto(prod)));
                refreshAll();
            });
            return del;
        });
        cartGrid.setItems(cart.values());
        Button order = new Button("Order");
        Button cancel = new Button("Cancel All");
        order.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        order.addClickListener(e -> orderCart());
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(e -> cancelCart());
        H2 h = new H2("My Cart");
        HorizontalLayout header = new HorizontalLayout(h, order, cancel);
        header.setWidthFull();
        header.expand(h);
        order.setHeightFull();
        cancel.setHeightFull();
        VerticalLayout cartLayout = new VerticalLayout(header, cartGrid);
        cartLayout.setWidth("50%");
        cartLayout.setHeightFull();
        add(cartLayout);
    }

    private void cancelCart() {
        cart.clear();
        fetchProducts();
        refreshAll();
    }

    /**
     *  To create an Order containing a certain amount of Items
     *  I first create an empty Order and then use its ID given to me by the db
     *  To create multiple order_product entities, one for each item
     *  I then call put/delete on the items amounts and on the customers money
     */
    private void orderCart() {
        if(!validateAndDecreaseMoney()){
            return;
        }
        //Create an empty order
        OrderDto newOne = new OrderDto(null, new Timestamp(System.currentTimeMillis()), "entered", Long.valueOf(id));
        Client client = ClientBuilder.newClient();
        Response res = client.target("http://localhost:8080/orders").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newOne, MediaType.APPLICATION_JSON));
        Long orderId = res.readEntity(OrderDto.class).getId();
        //Create an order_product entity for each item
        cart.forEach((itemId, item) -> {
            OrderProductDto orderProd = new OrderProductDto(null, orderId, itemId, item.getAmount());
            client.target("http://localhost:8080/order_products").request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(orderProd, MediaType.APPLICATION_JSON));
        });

        //Put/delete all the not-available items = decrease their amount
        //I cannot actually call DELETE, because I want to have them in the database
        cart.forEach((itemId, item) -> {
            Optional<ProductDto> ordered = fetchedProds.stream().filter(e -> e.getId().equals(itemId)).findFirst();
            ordered.ifPresentOrElse(or -> {
                client.target("http://localhost:8080/products/" + itemId).request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(or, MediaType.APPLICATION_JSON));},
                                    () -> {
                ProductDto unavailable = new ProductDto(item);
                unavailable.setId(itemId);
                unavailable.setAmount(0L);
                client.target("http://localhost:8080/products/" + itemId).request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(unavailable, MediaType.APPLICATION_JSON));});
        });
        cancelCart();
    }

    /**
     * Reduce the customers money balance by the cost of his cart
     * If he doesnt have enough, dont proceed with the order
     *
     * @return If the user had enough money
     */
    private boolean validateAndDecreaseMoney() {
        if(cart.isEmpty()){
            navigator.handle(null, "Your Cart Is Empty", false);
            return false;
        }
        Long totalCost = 0L;
        for(var i : cart.values()){
            totalCost += (i.getPrice() * i.getAmount());
        }
        Client client = ClientBuilder.newClient();
        Response res = client.target("http://localhost:8080/customers/" + id).request(MediaType.APPLICATION_JSON).get(Response.class);
        CustomerDto buyer = res.readEntity(CustomerDto.class);
        if(buyer.getMoney() < totalCost){
            navigator.handle(null, "Not Enough Money", false);
            return false;

        } else{
            buyer.changeMoney(-totalCost);
            client.target("http://localhost:8080/customers/" + id).request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(buyer, MediaType.APPLICATION_JSON));
            return true;
        }
    }

    private void addToCart() {
        products.addItemDoubleClickListener(e -> {
            ProductDto added = e.getItem();

            added.changeAmount(-1);
            if(added.getAmount() < 1){
                fetchedProds.remove(added);
            }
            Long id = added.getId();
            if(cart.containsKey(id)){
                cart.get(id).changeAmount(1);
            } else{
                cart.put(id, new ProductDto(added));
            }

            refreshAll();

        });
    }

    private void refreshAll() {
        products.setItems(fetchedProds);
        cartGrid.setItems(cart.values());
    }

    private void initGrid() {
        products.setSizeFull();
        products.setColumns("name", "price", "amount");
        fetchProducts();
        VerticalLayout productsLayout = new VerticalLayout(new H2("Available Products"), products);
        productsLayout.setWidth("50%");
        productsLayout.setHeightFull();
        add(productsLayout);
    }

    private void fetchProducts() {
        navigator.handle(id, "Vaše ID je null", true);
        Client client = ClientBuilder.newClient();
        Response res = client.target("http://localhost:8080/products").request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        List<ProductDto> fetched = res.readEntity(new GenericType<List<ProductDto>>(){});
        fetchedProds = fetched.stream().filter(e -> e.getAmount() > 0).collect(Collectors.toList());
    }
}
