package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Route(value = "/account", layout = MainLayout.class)
public class AccountView extends VerticalLayout {
    String id;
    CookieReader cook;
    NoIdNavigator navigator;
    CustomerDto account;

    AccountForm form;
    Client client = ClientBuilder.newClient();
    public AccountView(CookieReader cooker, NoIdNavigator nav){
        setSizeFull();
        cook = cooker;
        navigator = nav;
        id = cook.getCookie("id");
        fetchAccount();
        form = new AccountForm(account, client, cook);
        setHorizontalComponentAlignment(Alignment.CENTER, form);
        add(form);
    }

    private void fetchAccount() {
        navigator.handle(id, "Your ID is null", true);
        Response res = client.target("http://localhost:8080/customers/" + id).request(MediaType.APPLICATION_JSON_TYPE)
                .get(Response.class);
        account = res.readEntity(CustomerDto.class);
    }
}
