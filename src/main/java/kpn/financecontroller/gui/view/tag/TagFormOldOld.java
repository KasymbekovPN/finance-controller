// TODO: 02.08.2022 del
//package kpn.financecontroller.gui.view.tag;
//
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.gui.event.CloseFormEvent;
//import kpn.financecontroller.gui.event.DeleteFormEvent;
//import kpn.financecontroller.gui.event.SaveFormEvent;
//import kpn.financecontroller.gui.view.EditFormOld;
//
//// TODO: 02.08.2022 del
//final public class TagFormOldOld extends EditFormOld<Tag> {
//
//    private final TextField name = new TextField();
//
//    public TagFormOldOld() {
//        super(new Binder<>(Tag.class));
//        addClassName("tag-form");
//        binder.bindInstanceFields(this);
//
//        name.setLabel(getTranslation("gui.label.name"));
//        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));
//
//        add(
//                name,
//                createButtonsLayout()
//        );
//    }
//
//    @Override
//    protected SaveFormEvent<EditFormOld<Tag>, Tag> createSaveEvent() {
//        return new TagSaveFormEvent(this, value);
//    }
//
//    @Override
//    protected DeleteFormEvent<EditFormOld<Tag>, Tag> createDeleteEvent() {
//        return new TagDeleteFormEvent(this, value);
//    }
//
//    @Override
//    protected CloseFormEvent<EditFormOld<Tag>, Tag> createCloseEvent() {
//        return new TagCloseFormEvent(this);
//    }
//
//    public static class TagSaveFormEvent extends SaveFormEvent<EditFormOld<Tag>, Tag> {
//        public TagSaveFormEvent(EditFormOld<Tag> source, Tag value) {
//            super(source, value);
//        }
//    }
//
//    public static class TagDeleteFormEvent extends DeleteFormEvent<EditFormOld<Tag>, Tag> {
//        public TagDeleteFormEvent(EditFormOld<Tag> source, Tag value) {
//            super(source, value);
//        }
//    }
//
//    public static class TagCloseFormEvent extends CloseFormEvent<EditFormOld<Tag>, Tag> {
//        public TagCloseFormEvent(EditFormOld<Tag> source) {
//            super(source);
//        }
//    }
//}
