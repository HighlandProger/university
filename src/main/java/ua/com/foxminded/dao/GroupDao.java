package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Group;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupDao implements CrudDao<Group> {

    private static final String CREATE_SQL = "INSERT INTO groups (department_id, course_id, group_number) VALUES (?,?,?) RETURNING groups.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM groups WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM groups;";
    private static final String DELETE_SQL = "DELETE FROM groups WHERE id = ?;";
    private static final Logger logger = LoggerFactory.getLogger(GroupDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group create(Group group) {

        logger.debug("Creating new group");
        Group createdGroup = jdbcTemplate.queryForObject(CREATE_SQL, new BeanPropertyRowMapper<>(Group.class), group.getDepartmentId(), group.getCourseId(), group.getGroupNumber());
        logger.debug("Group has been created");
        return createdGroup;
    }

    @Override
    public Optional<Group> getById(long id) {

        logger.debug("Getting group by id = {}", id);
        try {
            Group obtainedGroup = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new BeanPropertyRowMapper<>(Group.class), id);
            logger.debug("Group with id ={} has been obtained", id);
            return Optional.ofNullable(obtainedGroup);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("Couldn't find group with id = {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Group> getAll() {

        logger.debug("Getting all groups");
        List<Group> obtainedGroups = jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Group.class));
        logger.debug("All groups have been obtained");
        return obtainedGroups;
    }

    @Override
    public void delete(long id) {

        logger.debug("Deleting group with id = {}", id);
        jdbcTemplate.update(DELETE_SQL, id);
        logger.debug("Group with id = {} has been deleted", id);
    }
}
