package kpn.financecontroller.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcTemplateConfigTest {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public JdbcTemplateConfigTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Test
    void shouldCheckDataSource() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    void shouldCheckJdbcTemplate() {
        assertThat(jdbcTemplate).isNotNull();
    }
}