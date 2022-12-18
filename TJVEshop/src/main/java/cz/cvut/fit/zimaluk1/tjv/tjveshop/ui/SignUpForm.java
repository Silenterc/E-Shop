package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.h2.fulltext.FullText.closeAll;

public class SignUpForm extends FormLayout {
    TextField name = new TextField("Jméno");
    EmailField email = new EmailField("E-Mail");
    TextField address = new TextField("Adresa");
    TextField money = new TextField("Peníze");
    Button save = new Button("Registrovat");
    Button close = new Button("Zrušit");

    Binder<CustomerDto> binder = new BeanValidationBinder<>(CustomerDto.class);
    public SignUpForm(){
        init();
        add(name, email, address, money, createButtonsLayout());
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

    public void init(){
        setHeight("70%");
        setWidth("30%");

        money.setHelperText("CZK");
        money.setAllowedCharPattern("[0-9]");

        binder.bindInstanceFields(this);
        binder.setBean(new CustomerDto());

        save.addClickListener(click -> addCustomer());
        close.addClickListener(click -> getUI().ifPresent(ui -> ui.navigate("")));
    }

    private void addCustomer() {
        if(binder.isValid()){

            Client client = ClientBuilder.newClient();
            CustomerDto newOne = binder.getBean();
            Response res = client.target("http://localhost:8080/customers").request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(newOne, MediaType.APPLICATION_JSON));

            CustomerDto added = res.readEntity(CustomerDto.class);
            Dialog idPopup = new Dialog();
            idPopup.setHeaderTitle("Your ID is: " + added.getId());
            Button endButton = new Button(new Icon("lumo", "cross"));
            endButton.addClickListener(
                    click -> closeAll(idPopup));
            endButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            idPopup.getHeader().add(endButton);
            add(idPopup);
            idPopup.open();
        }
    }
    private void closeAll(Dialog idPopup){
        idPopup.close();
        getUI().ifPresent(ui -> ui.navigate(""));

    }
}
