package cz.cvut.fit.zimaluk1.tjv.tjveshop.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/signup")
public class SignUpView extends VerticalLayout {
    public final SignUpForm form;
    public SignUpView(){
        setSizeFull();
        form = new SignUpForm();
        setHorizontalComponentAlignment(Alignment.CENTER, form);
        add(form);

    }
}
