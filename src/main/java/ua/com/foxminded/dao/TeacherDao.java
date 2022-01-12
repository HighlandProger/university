package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Teacher;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDao implements CrudDao<Teacher> {

    private final Logger logger = LoggerFactory.getLogger(TeacherDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher create(Teacher teacher) {

        logger.info("Creating new teacher");
        String sql = "INSERT INTO teachers (department_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING teachers.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Teacher.class),
            teacher.getDepartmentId(), teacher.getFirstName(), teacher.getLastName(), teacher.getAge());
    }

    @Override
    public Optional<Teacher> getById(long id) {

        logger.info("Getting teacher by id = {}", id);
        String sql = "SELECT * FROM teachers WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Teacher.class), id));
    }

    @Override
    public List<Teacher> getAll() {

        logger.info("Getting all teachers");
        String sql = "SELECT * FROM teachers;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Teacher.class));
    }

    @Override
    public void delete(long id) {

        logger.info("Deleting teacher with id = {}", id);
        String sql = "DELETE FROM teachers WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
