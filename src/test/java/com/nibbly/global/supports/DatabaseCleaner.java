package com.nibbly.global.supports;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleaner(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void executeTruncate() {
        setReferentialIntegrity(false);
        truncateTable(getTableNames());
        setReferentialIntegrity(true);
    }

    private void truncateTable(List<String> tableNames) {
        tableNames.forEach(tableName -> jdbcTemplate.execute("TRUNCATE TABLE " + tableName));
    }

    private void setReferentialIntegrity(boolean enabled) {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY " + (enabled ? "TRUE" : "FALSE"));
    }

    private List<String> getTableNames() {
        List<String> tableNames = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'",
                String.class
        );
        return tableNames;
    }
}
