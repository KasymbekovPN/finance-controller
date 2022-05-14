package kpn.financecontroller.gui.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class OptionalAttributeProcessor<ATTRIBUTE> implements AttributeProcessor<Boolean, ATTRIBUTE> {
    private final Function<ATTRIBUTE, Boolean> calculator;
    private final Consumer<ATTRIBUTE> clearer;

    @Getter
    @Setter
    private Boolean status = false;

    @Override
    public void calculate(ATTRIBUTE attribute) {
        status = calculator.apply(attribute);
    }

    @Override
    public void clear(ATTRIBUTE attribute) {
        if (!status){
            clearer.accept(attribute);
        }
    }
}
