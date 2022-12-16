package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/products", layout = MainLayout.class)
public class ProductsView extends VerticalLayout {
    String id;
    CookieReader cook;
    public ProductsView(CookieReader cooker){
        setSizeFull();
        cook = cooker;
        //ID VERIFIKACI UDELAM AZ PRI PRVNI AKCI UZIVATELE
        id = cook.getCookie("id");
    }
}
