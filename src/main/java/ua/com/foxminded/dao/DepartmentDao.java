package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Department;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDao implements CrudDao<Department> {

    private static final String CREATE_SQL = "INSERT INTO departments (name) VALUES (?) RETURNING departments.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM departments WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM departments;";
    private static final String DELETE_SQL = "DELETE FROM departments WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE departments SET name = ? WHERE id = ?";
    private static final Logger logger = LoggerFactory.getLogger(DepartmentDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Department create(Department department) {

        logger.debug("Creating new department");
        Department createdDepartment = jdbcTemplate.queryForObject(CREATE_SQL, new BeanPropertyRowMapper<>(Department.class), department.getName());
        logger.debug("Department has been created");
        return createdDepartment;
    }

    @Override
    public Optional<Department> getById(long id) {

        logger.debug("Getting department by id = {}", id);
        try {
            Department obtainedDepartment = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new BeanPropertyRowMapper<>(Department.class), id);
            logger.debug("Department with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedDepartment);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find department with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Department> getAll() {

        logger.debug("Getting all departments");
        List<Department> obtainedDepartments = jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Department.class));
        logger.debug("All departments have been obtained");
        return obtainedDepartments;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting department with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Department with id = {} has been deleted", id);
    }

    @Override
    public void update(long id, Department department) {

        logger.debug("Updating department with id = {}", id);
        jdbcTemplate.update(UPDATE_SQL, department.getName(), id);
        logger.debug("Department with id = {} has been updated", id);
    }
}
