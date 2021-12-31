package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.domain.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {

        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setEstablishYear(rs.getInt("establish_year"));

        return course;
    }
}
