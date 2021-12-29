package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.CrudDao;
import ua.com.foxminded.dao.mapper.GroupMapper;
import ua.com.foxminded.domain.Group;

import javax.sql.DataSource;
import java.util.List;

public class GroupDao implements CrudDao<Group> {

    private final JdbcTemplate jdbcTemplate;

    public GroupDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group create(Group group) {

        String sql = "INSERT INTO groups (id, department_id, course_id, group_number) VALUES (?,?,?,?) RETURNING groups.*;";
        return jdbcTemplate.query(sql, new GroupMapper(),
            group.getId(), group.getDepartmentId(), group.getCourseId(), group.getGroupNumber()).
            stream().findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public Group getById(long id) {

        String sql = "SELECT * FROM groups WHERE id = ?;";
        return jdbcTemplate.query(sql, new GroupMapper(), id)
            .stream().findAny().orElse(null);
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