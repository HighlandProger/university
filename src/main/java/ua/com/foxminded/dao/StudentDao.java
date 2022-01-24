package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Student;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao implements CrudDao<Student> {

    private static final String CREATE_SQL = "INSERT INTO students (group_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING students.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM students WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM students;";
    private static final String DELETE_SQL = "DELETE FROM students WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ?, age =? WHERE id = ?" ;
    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student create(Student student) {

        logger.debug("Creating new student");
        Student createdStudent = jdbcTemplate.queryForObject(CREATE_SQL, new BeanPropertyRowMapper<>(Student.class), student.getGroupId(), student.getFirstName(), student.getLastName(), student.getAge());
        logger.debug("Student has been created");
        return createdStudent;
    }

    @Override
    public Optional<Student> getById(long id) {

        logger.debug("Getting student by id = {}", id);
        try {
            Student obtainedStudent = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new BeanPropertyRowMapper<>(Student.class), id);
            logger.debug("Student with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedStudent);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find student with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Student> getAll() {

        logger.debug("Getting all students");
        List<Student> obtainedStudents = jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Student.class));
        logger.debug("All students have been obtained");
        return obtainedStudents;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting student with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Student with id = {} has been deleted", id);
    }

    @Override
    public void update(long id, Student student) {

        logger.debug("Updating student with id = {}", id);
        jdbcTemplate.update(UPDATE_SQL,
            student.getGroupId(), student.getFirstName(), student.getLastName(), student.getAge(), id);
        logger.debug("Student with id = {} has been updated", id);
    }
}
