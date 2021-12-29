package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.CrudDao;
import ua.com.foxminded.dao.mapper.DepartmentMapper;
import ua.com.foxminded.domain.Department;

import javax.sql.DataSource;
import java.util.List;

public class DepartmentDao implements CrudDao<Department> {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Department create(Department department) {

        String sql = "INSERT INTO departments (id, name) VALUES (?,?) RETURNING departments.*;";
        return jdbcTemplate.query(sql, new DepartmentMapper(),
            department.getId(), department.getName()).
            stream().findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public Department getById(long id) {

        String sql = "SELECT * FROM departments WHERE id = ?;";
        return jdbcTemplate.query(sql, new DepartmentMapper(), id)
            .stream().findAny().orElse(null);
    }

    @Override
    public List<Department> getAll() {

        String sql = "SELECT * FROM departments;";
        return jdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM departments WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}