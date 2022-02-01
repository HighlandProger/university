package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.ClassRoom;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ClassRoomDao implements CrudDao<ClassRoom> {

    private static final String CREATE_SQL = "INSERT INTO classrooms (class_number) VALUES (?) RETURNING classrooms.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM classrooms WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM classrooms;";
    private static final String DELETE_SQL = "DELETE FROM classrooms WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE classrooms SET class_number = ? WHERE id = ?";
    private static final Logger logger = LoggerFactory.getLogger(ClassRoomDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassRoomDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ClassRoom create(ClassRoom classRoom) {

        logger.debug("Creating new class room");
        ClassRoom createdClassRoom = jdbcTemplate.queryForObject(CREATE_SQL, new BeanPropertyRowMapper<>(ClassRoom.class), classRoom.getClassNumber());
        logger.debug("Class room has been created");
        return createdClassRoom;
    }

    @Override
    public Optional<ClassRoom> getById(long id) {

        logger.debug("Getting class room by id = {}", id);
        try {
            ClassRoom obtainedClassRoom = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new BeanPropertyRowMapper<>(ClassRoom.class), id);
            logger.debug("Class room with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedClassRoom);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find class room with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<ClassRoom> getAll() {

        logger.debug("Getting all class rooms");
        List<ClassRoom> obtainedClassRooms = jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(ClassRoom.class));
        logger.debug("All class rooms have been obtained");
        return obtainedClassRooms;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting class room with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Class room with id = {} has been deleted", id);
    }

    @Override
    public void update(long id, ClassRoom classRoom) {

        logger.debug("Updating class room with id = {}", id);
        jdbcTemplate.update(UPDATE_SQL, classRoom.getClassNumber(), id);
        logger.debug("Class room with id = {} has been updated", id);
    }
}
