package kpn.financecontroller.gui.external.builder;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.textfield.TextArea;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MultilineComponentBuilderTest {

    @Test
    void shouldCheckBuilding_withoutText() {
        HasSize component = new MultilineComponentBuilder().build();
        assertThat(component).isNotNull();
        assertThat(component.getClass()).isEqualTo(TextArea.class);

        String value = ((TextArea) component).getValue();
        assertThat(value).isEmpty();
    }

    @Test
    void shouldCheckBuilding_withText() {
        List<String> lines = List.of("line 0", "line 1", "line 2");

        MultilineComponentBuilder builder = new MultilineComponentBuilder();

        StringBuilder expectedValue = new StringBuilder();
        String delimiter = "";
        for (String line : lines) {
            builder.append(line);
            expectedValue.append(delimiter).append(line);
            delimiter = "\n";
        }

        HasSize component = builder.build();

        assertThat(component).isNotNull();
        assertThat(component.getClass()).isEqualTo(TextArea.class);

        String value = ((TextArea) component).getValue();
        assertThat(value).isEqualTo(expectedValue.toString());
    }
}