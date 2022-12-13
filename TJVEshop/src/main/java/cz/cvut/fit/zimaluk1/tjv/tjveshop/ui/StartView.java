package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.annotation.WebServlet;

@Route("")
@WebServlet(urlPatterns = {"/app/*", "/VAADIN/*"}, asyncSupported = true)
public class StartView extends VerticalLayout {

    public StartView(){
        setSizeFull();
        Button sign = new Button("Sign Up");
        sign.addClickListener(e ->
                sign.getUI().ifPresent(ui ->
                        ui.navigate("/signup"))
        );
        Button log = new Button("Login");
        log.addClickListener(e ->
                log.getUI().ifPresent(ui ->
                        ui.navigate("/login"))
        );
        VerticalLayout greet = new VerticalLayout();
        greet.setHeightFull();
        greet.add(sign);
        greet.add(log);
        greet.setHorizontalComponentAlignment(Alignment.CENTER, sign, log);
        greet.setJustifyContentMode(JustifyContentMode.CENTER);
        //setHorizontalComponentAlignment(Alignment.CENTER, greet);
        add(greet);

    }
}
