package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.mapper.GroupMapper;
import ua.com.foxminded.domain.Group;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class GroupDao implements CrudDao<Group> {

    private final JdbcTemplate jdbcTemplate;

    public GroupDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group create(Group group) {

        String sql = "INSERT INTO groups (id, department_id, course_id, group_number) VALUES (?,?,?,?) RETURNING groups.*;";
        return jdbcTemplate.queryForObject(sql, new GroupMapper(),
            group.getId(), group.getDepartmentId(), group.getCourseId(), group.getGroupNumber());
    }

    @Override
    public Optional<Group> getById(long id) {

        String sql = "SELECT * FROM groups WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new GroupMapper(), id));
    }

    @Override
    public List<Group> getAll() {

        String sql = "SELECT * FROM groups;";
        return jdbcTemplate.query(sql, new GroupMapper());
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM groups WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
