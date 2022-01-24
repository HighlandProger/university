package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Course;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDao implements CrudDao<Course> {

    private static final String CREATE_SQL = "INSERT INTO courses (establish_year) VALUES (?) RETURNING courses.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM courses WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM courses;";
    private static final String DELETE_SQL = "DELETE FROM courses WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE courses SET establish_year = ? WHERE id = ?" ;
    private static final Logger logger = LoggerFactory.getLogger(CourseDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Course create(Course course) {

        logger.debug("Creating new course");
        Course createdCourse = jdbcTemplate.queryForObject(CREATE_SQL, new BeanPropertyRowMapper<>(Course.class), course.getEstablishYear());
        logger.debug("Course has been created");
        return createdCourse;
    }

    @Override
    public Optional<Course> getById(long id) {

        logger.debug("Getting course by id = {}", id);
        try {
            Course obtainedCourse = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new BeanPropertyRowMapper<>(Course.class), id);
            logger.debug("Course with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedCourse);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find course with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getAll() {

        logger.debug("Getting all courses");
        List<Course> obtainedCourses = jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Course.class));
        logger.debug("All courses have been obtained");
        return obtainedCourses;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting course with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Course with id = {} has been deleted", id);
    }

    @Override
    public void update(long id, Course course) {

        logger.debug("Updating course with id = {}", id);
        jdbcTemplate.update(UPDATE_SQL, course.getEstablishYear(), id);
        logger.debug("Course with id = {} has been updated", id);
    }
}
