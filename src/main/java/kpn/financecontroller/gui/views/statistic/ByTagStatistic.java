package kpn.financecontroller.gui.views.statistic;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.data.services.statistic.byTag.ByTagStatisticService;
import kpn.financecontroller.data.services.statistic.byTag.query.QueryOld;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.seed.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Scope("prototype")
@Route(value = "byTagStatistic", layout = MainLayout.class)
@PermitAll
public class ByTagStatistic extends VerticalLayout implements HasDynamicTitle {

    private final MultiSelectListBox<Tag> tags = createMultiSelectListBox(this::callOnMultiSelectListBoxValueChanging);
    private final DatePicker beginTime = createDatePicker(this::callOnStartDatePickerStateChanging);
    private final DatePicker endTime = createDatePicker(this::callOnEndDatePickerStateChanging);
    private final TextArea output = new TextArea();

    private final ProductTask productTask = new ProductTask();
    private final PaymentTask paymentTask = new PaymentTask();

    @Autowired
    private DTOService<Tag, TagEntity> tagDtoService;
    @Autowired
    private ClassAliasGenerator classAliasGenerator;
    @Autowired
    private ByTagStatisticService<Task, Seed> byTagStatisticService;

    @PostConstruct
    public void init(){
        setSizeFull();
        add(createToolBar(), createContent());
    }

    @Override
    public String getPageTitle() {
        return getTranslation(classAliasGenerator.generate(getClass()));
    }

    private Component createToolBar() {
        HorizontalLayout toolbar = new HorizontalLayout(
                createCheckBox("", this::callOnStartCheckBoxStateChanging),
                beginTime,
                createCheckBox("", this::callOnEndCheckBoxStateChanging),
                endTime,
                createButton(this::callOnButtonClick)
        );
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component createContent() {
        Component selectArea = createSelectArea();
        HorizontalLayout content = new HorizontalLayout(
                createReportArea(),
                selectArea
        );
        content.setSizeFull();
        content.setClassName("content");

        return content;
    }

    private Button createButton(ComponentEventListener<ClickEvent<Button>> listener) {
        Button button = new Button(getTranslation("gui.button.calculateStatistic"));;
        button.addClickListener(listener);
        return button;
    }

    private Checkbox createCheckBox(String code, HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> listener) {
        String label = code.isEmpty() ? "" : getTranslation(code);
        Checkbox checkbox = new Checkbox(label, false);
        checkbox.addValueChangeListener(listener);
        return checkbox;
    }

    private DatePicker createDatePicker(HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        DatePicker datePicker = new DatePicker();
        datePicker.setEnabled(false);
        datePicker.addValueChangeListener(listener);
        return datePicker;
    }

    private Div createReportArea() {
        output.setSizeFull();
        output.setValue("hello \n world");// TODO: 06.06.2022 del
        output.setReadOnly(true);
        Div div = new Div(output);
        div.setSizeFull();
        return div;
    }

    private VerticalLayout createSelectArea() {
        configureTags();
        VerticalLayout selectArea = new VerticalLayout(
                createCheckBox("gui.label.forAllTags", this::callOnAllTagsCheckBoxStateChanging),
                tags
        );
        selectArea.setSizeFull();
        selectArea.setClassName("selectArea");

        return selectArea;
    }

    private MultiSelectListBox<Tag> createMultiSelectListBox(HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<MultiSelectListBox<Tag>, Set<Tag>>> listener) {
        MultiSelectListBox<Tag> multiSelectListBox = new MultiSelectListBox<>();
        multiSelectListBox.addValueChangeListener(listener);
        return multiSelectListBox;
    }

    private void configureTags() {
        tags.setItems(tagDtoService.loader().all().getValue());
    }

    private void callOnStartCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        Boolean enabled = event.getValue();
        paymentTask.setBeginTimeEnable(enabled);
        beginTime.setEnabled(enabled);
        if (!enabled){
            paymentTask.setBeginTime(null);
        }
    }

    private void callOnStartDatePickerStateChanging(AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {
        paymentTask.setBeginTime(event.getValue());
    }

    private void callOnEndCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        Boolean enabled = event.getValue();
        paymentTask.setEndTimeEnable(enabled);
        endTime.setEnabled(enabled);
        if (!enabled){
            paymentTask.setEndTime(null);
        }
    }

    private void callOnEndDatePickerStateChanging(AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {
        paymentTask.setEndTime(event.getValue());
    }

    private void callOnAllTagsCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        productTask.setAllTags(event.getValue());
    }

    private void callOnMultiSelectListBoxValueChanging(AbstractField.ComponentValueChangeEvent<MultiSelectListBox<Tag>, Set<Tag>> event) {
        productTask.setTags(event.getValue());
    }

    private void callOnButtonClick(ClickEvent<?> event) {
        // TODO: 06.06.2022 del
//        System.out.println(productTask);
//        System.out.println(paymentTask);

        Seed seed = byTagStatisticService.calculate(ProductTask.copy(productTask), PaymentTask.copy(paymentTask));

        // TODO: 06.06.2022 del
        System.out.println("----");
        System.out.println(seed.getCode());
        for (Object arg : seed.getArgs()) {
            System.out.println(arg);
        }
    }
}
