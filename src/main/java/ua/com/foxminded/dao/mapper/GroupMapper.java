package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.domain.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();
        group.setId(rs.getLong("id"));
        group.setDepartmentId(rs.getLong("department_id"));
        group.setCourseId(rs.getLong("course_id"));
        group.setGroupNumber(rs.getInt("group_number"));

        return group;
    }
}
