package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Teacher;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDao implements CrudDao<Teacher> {

    private static final String CREATE_SQL = "INSERT INTO teachers (department_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING teachers.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM teachers WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM teachers;";
    private static final String DELETE_SQL = "DELETE FROM teachers WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE teachers SET department_id = ?, first_name = ?, last_name = ?, age =? WHERE id = ?" ;
    private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher create(Teacher teacher) {

        logger.debug("Creating new teacher");
        Teacher createdTeacher = jdbcTemplate.queryForObject(CREATE_SQL, new BeanPropertyRowMapper<>(Teacher.class), teacher.getDepartmentId(), teacher.getFirstName(), teacher.getLastName(), teacher.getAge());
        logger.debug("Teacher has been created");
        return createdTeacher;
    }

    @Override
    public Optional<Teacher> getById(long id) {

        logger.info("Getting teacher by id = {}", id);
        try {
            Teacher obtainedTeacher = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new BeanPropertyRowMapper<>(Teacher.class), id);
            logger.debug("Teacher with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedTeacher);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find teacher with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Teacher> getAll() {

        logger.debug("Getting all teachers");
        List<Teacher> obtainedTeachers = jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Teacher.class));
        logger.debug("All teachers have been obtained");
        return obtainedTeachers;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting teacher with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Teacher with id = {} has been deleted", id);
    }

    @Override
    public void update(long id, Teacher teacher){

        logger.debug("Updating student with id = {}", id);
        jdbcTemplate.update(UPDATE_SQL,
            teacher.getDepartmentId(), teacher.getFirstName(), teacher.getLastName(), teacher.getAge(), id);
        logger.debug("Student with id = {} has been updated", id);
    }
}
