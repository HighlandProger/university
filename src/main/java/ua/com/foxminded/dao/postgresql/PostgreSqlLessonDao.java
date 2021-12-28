package ua.com.foxminded.dao.postgresql;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.CRUD;
import ua.com.foxminded.dao.mapper.LessonMapper;
import ua.com.foxminded.domain.Lesson;

import java.util.List;

public class PostgreSqlLessonDao implements CRUD<Lesson> {

    private final JdbcTemplate jdbcTemplate;

    public PostgreSqlLessonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Lesson create(Lesson lesson) {

        String sql = "INSERT INTO lessons (name, teacher_id, group_id, date) VALUES (?,?,?,?) RETURNING lessons.*;";
        return jdbcTemplate.query(sql, new LessonMapper(),
            lesson.getId(), lesson.getTeacherId(), lesson.getGroupId(), lesson.getDate()).
            stream().findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public Lesson getById(long id) {

        String sql = "SELECT * FROM lessons WHERE id = ?;";
        return jdbcTemplate.query(sql, new LessonMapper(), id)
            .stream().findAny().orElse(null);
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
