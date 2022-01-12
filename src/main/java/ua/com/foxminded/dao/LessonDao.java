package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Lesson;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class LessonDao implements CrudDao<Lesson> {

    private final Logger logger = LoggerFactory.getLogger(LessonDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lesson create(Lesson lesson) {

        logger.info("Creating new lesson");
        String sql = "INSERT INTO lessons (name, teacher_id, group_id, date_time) VALUES (?,?,?,?) RETURNING lessons.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Lesson.class),
            lesson.getName(), lesson.getTeacherId(), lesson.getGroupId(), Timestamp.valueOf(lesson.getDateTime()));
    }

    @Override
    public Optional<Lesson> getById(long id) {

        logger.info("Getting lesson by id = {}", id);
        String sql = "SELECT * FROM lessons WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Lesson.class), id));
    }

    @Override
    public List<Lesson> getAll() {

        logger.info("Getting all lessons");
        String sql = "SELECT * FROM lessons;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Lesson.class));
    }

    @Override
    public void delete(long id) {

        logger.info("Deleting lesson with id = {}", id);
        String sql = "DELETE FROM lessons WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

}
