package kpn.financecontroller.initialization.setting;

import kpn.financecontroller.initialization.generators.valued.Entities;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SettingImplTest {

    @Test
    void shouldCheckIsEnable_withoutSet() {
        SettingImpl setting = new SettingImpl();
        assertThat(setting.isEnable()).isFalse();
    }

    @Test
    void shouldCheckIsEnable() {
        SettingImpl setting = new SettingImpl();
        setting.setEnable(true);
        assertThat(setting.isEnable()).isTrue();
    }

    @Test
    void shouldCheckPathCalculation() {
        String directory = "some.directory";

        SettingImpl setting = new SettingImpl();
        setting.setDirectory(directory);
        String path = setting.getPath(Entities.TAGS);

        assertThat(path).isEqualTo(directory + "/tags.json");
    }
}