package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.rowmapper.LessonRowMapper;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class LessonDao implements CrudDao<Lesson> {

    private static final String CREATE_SQL = "INSERT INTO lessons (name, teacher_id, group_id, date_time) VALUES (?,?,?,?) RETURNING lessons.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM lessons WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM lessons;";
    private static final String DELETE_SQL = "DELETE FROM lessons WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE lessons SET name = ?, group_id = ?, teacher_id = ?, date_time = ? WHERE id = ?";
    private static final Logger logger = LoggerFactory.getLogger(LessonDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lesson create(Lesson lesson) {

        logger.debug("Creating new lesson");
        Lesson createdLesson = jdbcTemplate.queryForObject(CREATE_SQL, new LessonRowMapper(), lesson.getName(), lesson.getTeacherId(), lesson.getGroupId(), Timestamp.valueOf(lesson.getDateTime()));
        logger.debug("Lesson has been created");
        return createdLesson;
    }

    @Override
    public Optional<Lesson> getById(long id) {

        logger.info("Getting lesson by id = {}", id);
        try {
            Lesson obtainedLesson = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new LessonRowMapper(), id);
            logger.debug("Lesson with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedLesson);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find lesson with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Lesson> getAll() {

        logger.debug("Getting all lessons");
        List<Lesson> obtainedLessons = jdbcTemplate.query(GET_ALL_SQL, new LessonRowMapper());
        logger.debug("All lessons have been obtained");
        return obtainedLessons;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting lesson with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Lesson with id = {} has been deleted", id);
    }

    @Override
    public void update(long id, Lesson lesson) {

        logger.debug("Updating lesson with id = {}", id);
        jdbcTemplate.update(UPDATE_SQL,
            lesson.getName(), lesson.getGroupId(), lesson.getTeacherId(), lesson.getDateTime(), id);
        logger.debug("Lesson with id = {} has been updated", id);
    }
}
