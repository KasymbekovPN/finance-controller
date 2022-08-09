package kpn.financecontroller.data.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActionTest {

    @Test
    void shouldCheckGetInfo() {
        String description = "some description";

        Action action = new Action();
        action.setDescription(description);

        assertThat(action.getInfo()).isEqualTo(description);
    }
}