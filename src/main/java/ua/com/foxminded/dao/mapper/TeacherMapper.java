package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.domain.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {

        Teacher teacher = new Teacher();
        teacher.setId(rs.getLong("id"));
        teacher.setDepartmentId(rs.getLong("department_id"));
        teacher.setFirstName(rs.getString("first_name"));
        teacher.setLastName(rs.getString("last_name"));
        teacher.setAge(rs.getInt("age"));

        return teacher;
    }
}
