package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Route(value = "/login", layout = StartLayout.class)
public class LoginView extends VerticalLayout {
    PasswordField login = new PasswordField();
    CookieReader cook;
    NoIdNavigator navigator;
    public LoginView(CookieReader cooker, NoIdNavigator nav){
        cook = cooker;
        navigator = nav;
        setWidthFull();
        setHeight("50%");
        init();

    }
    public void init(){
        login.setLabel("Vaše ID");
        login.setPrefixComponent(VaadinIcon.USER.create());
        login.setAllowedCharPattern("[0-9]");
        login.setErrorMessage("Not a valid ID");
        login.addKeyPressListener(Key.ENTER, e -> maybeLogin(login.getValue()));
        add(login);
        setHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
    public void maybeLogin(String value){
        Client client = ClientBuilder.newClient();
        CustomerDto newOne = new CustomerDto();
        Response res = client.target("http://localhost:8080/customers/" + value).request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        if(res.getStatus() == 200){
            cook.createCookie("id", value);
            //Need to send a new HTTP request so the cookies work as intended
            //This line of code has cost me hours of debugging
            UI.getCurrent().getPage().setLocation("/ui/products");

        } else{
            showError();
        }
    }
    public void showError(){
        navigator.handle(null, "ID nenalezeno", true);
    }
}
