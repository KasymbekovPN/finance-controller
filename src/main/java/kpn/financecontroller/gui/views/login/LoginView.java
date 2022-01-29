package kpn.financecontroller.gui.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterListener, HasDynamicTitle {

    private final LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setI18n(createLoginI18n());
        login.setAction("login");

        add(new H1(getTranslation("gui.login.header.title")), login);
    }

    @Override
    public String getPageTitle() {
        return getTranslation("gui.login");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")
        ) {
            login.setError(true);
        }
    }

    private LoginI18n createLoginI18n() {
        LoginI18n loginI18n = LoginI18n.createDefault();
        loginI18n.getForm().setTitle(getTranslation("gui.login.form.title"));
        loginI18n.getForm().setUsername(getTranslation("gui.login.form.userName"));
        loginI18n.getForm().setPassword(getTranslation("gui.login.form.password"));
        loginI18n.getForm().setForgotPassword(getTranslation("gui.login.form.forgotPassword"));
        loginI18n.getForm().setSubmit(getTranslation("gui.login.form.submit"));
        return loginI18n;
    }
}
