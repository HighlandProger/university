package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.mapper.LessonMapper;
import ua.com.foxminded.domain.Lesson;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class LessonDao implements CrudDao<Lesson> {

    private final JdbcTemplate jdbcTemplate;

    public LessonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lesson create(Lesson lesson) {

        String sql = "INSERT INTO lessons (name, teacher_id, group_id, date) VALUES (?,?,?,?) RETURNING lessons.*;";
        return jdbcTemplate.queryForObject(sql, new LessonMapper(),
            lesson.getId(), lesson.getTeacherId(), lesson.getGroupId(), lesson.getDate());
    }

    @Override
    public Optional<Lesson> getById(long id) {

        String sql = "SELECT * FROM lessons WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new LessonMapper(), id));
    }

    @Override
    public List<Lesson> getAll() {

        String sql = "SELECT * FROM lessons;";
        return jdbcTemplate.query(sql, new LessonMapper());
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM lessons WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}
