package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class AccountForm extends FormLayout {
    CustomerDto account;
    Client client;
    CookieReader cook;
    TextField name = new TextField("Name");
    EmailField email = new EmailField("E-Mail");
    TextField address = new TextField("Address");
    TextField money = new TextField("Money");
    Button save = new Button("Update");
    Button close = new Button("Cancel");

    Button logout = new Button("Log Out");

    Binder<CustomerDto> binder = new BeanValidationBinder<>(CustomerDto.class);
    public AccountForm(CustomerDto account, Client client, CookieReader cooker){
        this.account = account;
        this.client = client;
        this.cook = cooker;
        init();
        add(name, email, address, money, createButtonsLayout(), logout);


    }

    private Component createButtonsLayout() {
        HorizontalLayout opt = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickShortcut(Key.ESCAPE);
        opt.add(save, close);
        opt.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        opt.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return opt;
    }

    private void init() {
        // Show the current account data
        name.setPlaceholder(account.getName());
        email.setPlaceholder(account.getEmail());
        address.setPlaceholder(account.getAddress());
        money.setPlaceholder(account.getMoney().toString());

        setHeight("70%");
        setWidth("30%");

        money.setHelperText("CZK");
        money.setAllowedCharPattern("[0-9]");

        binder.bindInstanceFields(this);
        binder.setBean(new CustomerDto());

        save.addClickListener(click -> editCustomer());
        close.addClickListener(click -> getUI().ifPresent(ui -> ui.navigate("/products")));
        logout.addClickListener(click -> logout());
        logout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
    }
    //Remove the id cookie so the user actually logs out,
    //And take him to the main page
    private void logout() {
        cook.deleteCookie("id");
        getUI().ifPresent(ui -> ui.navigate(""));
    }
    //Correctly edit the fields that were changed and keep the rest
    //And send a put request
    private void editCustomer() {
        if(binder.isValid()){
            CustomerDto edited = binder.getBean();
            account.edit(edited);
            client.target("http://localhost:8080/customers/" + account.getId()).request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.entity(account, MediaType.APPLICATION_JSON));
        }
        getUI().ifPresent(ui -> ui.navigate("/products"));
    }
}
