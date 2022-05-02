package kpn.financecontroller.gui.generators;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ConfigurationProperties(prefix = "generators.alias.class")
class ClassAliasGeneratorImplTest {

    @Setter
    private String prefix;

    @Autowired
    private ClassAliasGeneratorImpl classAliasGenerator;

    @Test
    void shouldCheckGeneration() {
        String alias = classAliasGenerator.generate(ForAliasGenerationTestClass.class);
        String expectedAlias = prefix + ForAliasGenerationTestClass.class.getSimpleName();
        assertThat(expectedAlias).isEqualTo(alias);
    }

    private static class ForAliasGenerationTestClass {}
}