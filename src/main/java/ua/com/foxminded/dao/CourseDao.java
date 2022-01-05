package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.mapper.CourseMapper;
import ua.com.foxminded.domain.Course;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class CourseDao implements CrudDao<Course> {

    private final JdbcTemplate jdbcTemplate;

    public CourseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Course create(Course course) {

        String sql = "INSERT INTO courses (establish_year) VALUES (?) RETURNING courses.*;";
        return jdbcTemplate.queryForObject(sql, new CourseMapper(),
            course.getEstablishYear());
    }

    @Override
    public Optional<Course> getById(long id) {

        String sql = "SELECT * FROM courses WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new CourseMapper(), id));
    }

    @Override
    public List<Course> getAll() {

        String sql = "SELECT * FROM courses;";
        return jdbcTemplate.query(sql, new CourseMapper());
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM courses WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}
