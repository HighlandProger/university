package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Student;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao implements CrudDao<Student> {

    private final Logger logger = LoggerFactory.getLogger(StudentDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student create(Student student) {

        logger.info("Creating new student");
        String sql = "INSERT INTO students (group_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING students.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class),
            student.getGroupId(), student.getFirstName(), student.getLastName(), student.getAge());
    }

    @Override
    public Optional<Student> getById(long id) {

        logger.info("Getting student by id = {}", id);
        String sql = "SELECT * FROM students WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), id));
    }

    @Override
    public List<Student> getAll() {

        logger.info("Getting all student");
        String sql = "SELECT * FROM students;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void delete(long id) {

        logger.info("Deleting student with id = {}", id);
        String sql = "DELETE FROM students WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
