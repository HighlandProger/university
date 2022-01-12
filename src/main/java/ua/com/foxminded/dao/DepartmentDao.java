package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Department;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDao implements CrudDao<Department> {

    private final Logger logger = LoggerFactory.getLogger(DepartmentDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Department create(Department department) {

        logger.info("Creating new department");
        String sql = "INSERT INTO departments (name) VALUES (?) RETURNING departments.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class),
            department.getName());
    }

    @Override
    public Optional<Department> getById(long id) {

        logger.info("Getting department by id = {}", id);
        String sql = "SELECT * FROM departments WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class), id));
    }

    @Override
    public List<Department> getAll() {

        logger.info("Getting all departments");
        String sql = "SELECT * FROM departments;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public void delete(long id) {

        logger.info("Deleting department with id = {}", id);
        String sql = "DELETE FROM departments WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}
