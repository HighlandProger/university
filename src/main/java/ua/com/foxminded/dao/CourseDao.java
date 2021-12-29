package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.CrudDao;
import ua.com.foxminded.dao.mapper.CourseMapper;
import ua.com.foxminded.domain.Course;

import javax.sql.DataSource;
import java.util.List;

public class CourseDao implements CrudDao<Course> {

    private final JdbcTemplate jdbcTemplate;

    public CourseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Course create(Course course) {

        String sql = "INSERT INTO courses (id, establish_year) VALUES (?,?) RETURNING courses.*;";
        return jdbcTemplate.query(sql, new CourseMapper(),
            course.getId(), course.getEstablishYear()).
            stream().findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public Course getById(long id) {

        String sql = "SELECT * FROM courses WHERE id = ?;";
        return jdbcTemplate.query(sql, new CourseMapper(), id)
            .stream().findAny().orElse(null);
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
