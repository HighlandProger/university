package ua.com.foxminded.dao;

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

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group create(Group group) {

        String sql = "INSERT INTO groups (department_id, course_id, group_number) VALUES (?,?,?) RETURNING groups.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Group.class),
            group.getDepartmentId(), group.getCourseId(), group.getGroupNumber());
    }

    @Override
    public Optional<Group> getById(long id) {

        String sql = "SELECT * FROM groups WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Group.class), id));
    }

    @Override
    public List<Group> getAll() {

        String sql = "SELECT * FROM groups;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM groups WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
