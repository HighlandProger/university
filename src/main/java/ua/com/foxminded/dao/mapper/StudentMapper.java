package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setGroupId(rs.getLong("group_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setAge(rs.getInt("age"));

        return student;
    }
}
