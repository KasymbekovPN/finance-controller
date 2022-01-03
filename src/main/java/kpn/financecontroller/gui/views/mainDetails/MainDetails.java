package kpn.financecontroller.gui.views.mainDetails;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.gui.views.MainLayout;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;

@PageTitle("Main details")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MainDetails extends VerticalLayout {

    // TODO: 03.01.2022 del 
//public class ListView extends VerticalLayout {
//
//    @Getter
//    private final Grid<Contact> grid = new Grid<Contact>(Contact.class);
//    private final TextField filterText = new TextField();
//    @Getter
//    private ContactForm form;
//    private CrmService crmService;
//

    public MainDetails() {
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        add(new Label("hello"));
    }


    // TODO: 03.01.2022 del
//    public ListView(CrmService crmService) {
//        this.crmService = crmService;
//        addClassName("list-view");
//        setSizeFull();
//
//        configureGrid();
//        configureForm();
//
//        add(getToolBar(), getContent());
//
//        updateList();
//        closeEditor();
//    }
//
//    private void closeEditor() {
//        form.setContact(null);
//        form.setVisible(false);
//        removeClassName("editing");
//    }
//
//    private void updateList() {
//        grid.setItems(crmService.findAllContacts(filterText.getValue()));
//    }
//
//    private Component getContent() {
//        HorizontalLayout content = new HorizontalLayout(grid, form);
//        content.addClassName("content");
//        content.setFlexGrow(2, grid);
//        content.setFlexGrow(1, form);
//        content.setSizeFull();
//        return content;
//    }
//
//    private void configureForm() {
//        form = new ContactForm(crmService.findAllCompanies(), crmService.findAllStatuses());
//        form.setWidth("25em");
//
//        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
//        form.addListener(ContactForm.DeleteEvent.class, this::deleteEvent);
//        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
//    }
//
//    private void deleteEvent(ContactForm.DeleteEvent event) {
//        crmService.deleteContact(event.getContact());
//        updateList();
//        closeEditor();
//    }
//
//    private void saveContact(ContactForm.SaveEvent event) {
//        crmService.saveContact(event.getContact());
//        updateList();
//        closeEditor();
//    }
//
//    private Component getToolBar() {
//        filterText.setPlaceholder("Filter by name...");
//        filterText.setClearButtonVisible(true);
//        filterText.setValueChangeMode(ValueChangeMode.LAZY);
//        filterText.addValueChangeListener(e -> updateList());
//
//        Button addContactButton = new Button("Add");
//        addContactButton.addClickListener(e -> addContact());
//
//        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
//        toolbar.addClassName("toolbar");
//
//        return toolbar;
//    }
//
//    private void addContact() {
//        grid.asSingleSelect().clear();
//        editContact(new Contact());
//    }
//
//    private void configureGrid() {
//        grid.addClassName("contact-grid");
//        grid.setSizeFull();
//        grid.setColumns("firstName", "lastName", "email");
//        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
//        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
//        grid.getColumns().forEach(column -> column.setAutoWidth(true));
//
//        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
//    }
//
//    private void editContact(Contact contact) {
//        if (contact == null){
//            closeEditor();
//        } else {
//            form.setContact(contact);
//            form.setVisible(true);
//            addClassName("editing");
//        }
//    }
//}


}
