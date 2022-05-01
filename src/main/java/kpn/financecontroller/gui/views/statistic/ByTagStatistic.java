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
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.views.MainLayout;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "byTagStatistic", layout = MainLayout.class)
@PermitAll
public class ByTagStatistic extends VerticalLayout implements HasDynamicTitle {

    private final MultiSelectListBox<Tag> tags = createMultiSelectListBox(this::callOnMultiSelectListBoxValueChanging);
    private final DatePicker beginTime = createDatePicker(this::callOnStartDatePickerStateChanging);
    private final DatePicker endTime = createDatePicker(this::callOnEndDatePickerStateChanging);

    private final State state = new State();

    private final DTOService<Tag, TagEntity, Long> tagDtoService;

    @Autowired
    public ByTagStatistic(DTOService<Tag, TagEntity, Long> tagDtoService) {
        this.tagDtoService = tagDtoService;
    }

    @PostConstruct
    public void init(){
        setSizeFull();
        add(createToolBar(), createContent());
    }

    @Override
    public String getPageTitle() {
        return getTranslation("gui.byTagStatistic");
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
        Button button = new Button(getTranslation("gui.calculateStatistic"));;
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
        // TODO: 01.05.2022 impl
        TextArea textArea = new TextArea();
        textArea.setSizeFull();
        textArea.setValue("hello \n world");
        textArea.setReadOnly(true);
        Div div = new Div(textArea);
        div.setSizeFull();
        return div;
    }

    private VerticalLayout createSelectArea() {
        configureTags();
        VerticalLayout selectArea = new VerticalLayout(
                createCheckBox("gui.forAllTags", this::callOnAllTagsCheckBoxStateChanging),
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
        state.setBeginTimeEnable(enabled);
        beginTime.setEnabled(enabled);
        if (!enabled){
            state.setBeginTime(null);
        }
    }

    private void callOnStartDatePickerStateChanging(AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {
        state.setBeginTime(event.getValue());
    }

    private void callOnEndCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        Boolean enabled = event.getValue();
        state.setEndTimeEnable(enabled);
        endTime.setEnabled(enabled);
        if (!enabled){
            state.setEndTime(null);
        }
    }

    private void callOnEndDatePickerStateChanging(AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {
        state.setEndTime(event.getValue());
    }

    private void callOnAllTagsCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        state.setForAllTags(event.getValue());
    }

    private void callOnMultiSelectListBoxValueChanging(AbstractField.ComponentValueChangeEvent<MultiSelectListBox<Tag>, Set<Tag>> event) {
        state.setTags(event.getValue());
    }

    private void callOnButtonClick(ClickEvent<?> event) {
        if (state.check()){
            // TODO: 01.05.2022 impl it
        }
    }

    @Getter
    @Setter
    private static class State {
        private boolean beginTimeEnable;
        private LocalDate beginTime;
        private boolean endTimeEnable;
        private LocalDate endTime;
        private boolean forAllTags;
        private Set<Tag> tags = new HashSet<>();

        public boolean check(){
            // TODO: 01.05.2022 impl it
            return false;
        }
    }
}
