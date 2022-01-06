package ua.com.foxminded.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.com.foxminded.domain.Department;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class DepartmentDao implements CrudDao<Department> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Department create(Department department) {

        String sql = "INSERT INTO departments (name) VALUES (?) RETURNING departments.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class),
            department.getName());
    }

    @Override
    public Optional<Department> getById(long id) {

        String sql = "SELECT * FROM departments WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class), id));
    }

    @Override
    public List<Department> getAll() {

        String sql = "SELECT * FROM departments;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM departments WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}
