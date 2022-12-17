package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.ProductDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

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
        cartGrid.setItems(cart.values());
        Button order = new Button("Order");
        order.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        order.addClickListener(e -> orderCart());
        H2 h = new H2("My Cart");
        HorizontalLayout header = new HorizontalLayout(h, order);
        header.setWidthFull();
        header.expand(h);
        order.setHeightFull();
        VerticalLayout cartLayout = new VerticalLayout(header, cartGrid);
        cartLayout.setWidth("50%");
        cartLayout.setHeightFull();
        add(cartLayout);
    }

    private void orderCart() {
    }

    private void addToCart() {
        products.addItemDoubleClickListener(e -> {
            ProductDto added = e.getItem();
            if(added.getAmount() > 0){
                added.changeAmount(-1);
                Long id = added.getId();
                if(cart.containsKey(id)){
                    cart.get(id).changeAmount(1);
                } else{
                    cart.put(id, new ProductDto(added));
                }
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
        navigator.handle(id, "Vaše ID je null");
        Client client = ClientBuilder.newClient();
        Response res = client.target("http://localhost:8080/products").request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        fetchedProds = res.readEntity(new GenericType<List<ProductDto>>(){});
    }
}
