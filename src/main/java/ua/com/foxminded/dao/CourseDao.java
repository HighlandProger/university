package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Course;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDao implements CrudDao<Course> {

    private final Logger logger = LoggerFactory.getLogger(CourseDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Course create(Course course) {

        logger.info("Creating new course");
        String sql = "INSERT INTO courses (establish_year) VALUES (?) RETURNING courses.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Course.class),
            course.getEstablishYear());
    }

    @Override
    public Optional<Course> getById(long id) {

        logger.info("Getting course by id = {}", id);
        String sql = "SELECT * FROM courses WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Course.class), id));
    }

    @Override
    public List<Course> getAll() {

        logger.info("Getting all courses");
        String sql = "SELECT * FROM courses;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public void delete(long id) {

        logger.info("Deleting course with id = {}", id);
        String sql = "DELETE FROM courses WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}
