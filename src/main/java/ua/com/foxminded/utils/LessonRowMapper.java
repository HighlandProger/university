package ua.com.foxminded.utils;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.model.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LessonRowMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("id");
        String name = rs.getString("name");
        Long groupId = rs.getLong("group_id");
        Long teacherId = rs.getLong("teacher_id");
        LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();

        return new Lesson(id, name, groupId, teacherId, dateTime);
    }
}
