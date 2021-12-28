package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.MissingResourceException;

public class SqlRunner {

    private static final String TABLES_SQL_FILE_NAME = "init.db";
    private final JdbcTemplate jdbcTemplate;

    public SqlRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTables() {

        String sql = readFile();
        jdbcTemplate.update(sql);
    }

    private String readFile() {

        StringBuilder output = new StringBuilder();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_SQL_FILE_NAME)) {
            if (inputStream == null) {
                throw new MissingResourceException("Cannot read file", SqlRunner.class.getName(), TABLES_SQL_FILE_NAME);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready()) {
                output.append(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();

    }

}
