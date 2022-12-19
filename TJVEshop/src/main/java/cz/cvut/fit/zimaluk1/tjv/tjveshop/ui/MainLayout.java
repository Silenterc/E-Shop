package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout() {
        initHeader();
    }
    public void initHeader(){
        H2 logo = new H2("E-shop");
        RouterLink products = new RouterLink("Produkty", ProductsView.class);
        RouterLink myOrders = new RouterLink("Moje Objednávky", OrdersView.class);
        RouterLink myAccount = new RouterLink("Můj Účet", AccountView.class);

        HorizontalLayout header = new HorizontalLayout(logo, products, myOrders, myAccount);
        header.setWidth("100%");
        header.expand(logo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);

    }





}
