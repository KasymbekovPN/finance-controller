package kpn.financecontroller.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JasyptDecConfigTest {

    private final StandardPBEStringEncryptor encryptor;

    @Autowired
    public JasyptDecConfigTest(StandardPBEStringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Test
    void shouldCheckEncryptor() {
        Assertions.assertThat(encryptor).isNotNull();
    }
}