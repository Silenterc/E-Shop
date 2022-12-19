package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class StartLayout extends AppLayout {

    public StartLayout(){
        initHeader();
    }

    private void initHeader() {
        H2 logo = new H2("E-shop");
        HorizontalLayout header = new HorizontalLayout(logo);
        header.setWidth("100%");
        header.expand(logo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }
}
