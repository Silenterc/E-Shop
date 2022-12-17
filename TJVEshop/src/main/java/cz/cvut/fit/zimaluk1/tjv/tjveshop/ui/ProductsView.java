package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.ProductDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Route(value = "/products", layout = MainLayout.class)
public class ProductsView extends VerticalLayout {
    String id;
    CookieReader cook;
    NoIdNavigator navigator;
    Grid<ProductDto> products = new Grid<>(ProductDto.class);
    public ProductsView(CookieReader cooker, NoIdNavigator nav){
        setSizeFull();
        cook = cooker;
        navigator = nav;
        id = cook.getCookie("id");
        initGrid();
        add(products);
    }

    private void initGrid() {
        products.setSizeFull();
        products.setColumns("name", "price", "amount");
        fetchProducts();
    }

    private void fetchProducts() {
        navigator.handle(id, "Vaše ID je null");
        Client client = ClientBuilder.newClient();
        Response res = client.target("http://localhost:8080/products").request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        List<ProductDto> prods = res.readEntity(new GenericType<List<ProductDto>>(){});
        products.setItems(prods);
    }
}
