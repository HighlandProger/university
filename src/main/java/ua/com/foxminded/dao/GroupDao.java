package ua.com.foxminded.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.Group;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupDao implements CrudDao<Group> {

    private final Logger logger = LoggerFactory.getLogger(GroupDao.class.getName());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group create(Group group) {

        logger.info("Creating new group");
        String sql = "INSERT INTO groups (department_id, course_id, group_number) VALUES (?,?,?) RETURNING groups.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Group.class),
            group.getDepartmentId(), group.getCourseId(), group.getGroupNumber());
    }

    @Override
    public Optional<Group> getById(long id) {

        logger.info("Getting group by id = {}", id);
        String sql = "SELECT * FROM groups WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Group.class), id));
    }

    @Override
    public List<Group> getAll() {

        logger.info("Getting all groups");
        String sql = "SELECT * FROM groups;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public void delete(long id) {

        logger.info("Deleting group with id = {}", id);
        String sql = "DELETE FROM groups WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
