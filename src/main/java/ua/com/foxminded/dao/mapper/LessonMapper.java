package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.domain.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getLong("id"));
        lesson.setName(rs.getString("name"));
        lesson.setTeacherId(rs.getLong("teacher_id"));
        lesson.setGroupId(rs.getLong("group_id"));
        lesson.setDate(rs.getDate("date"));

        return lesson;
    }
}
