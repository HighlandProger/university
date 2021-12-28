package ua.com.foxminded.dao.postgresql;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.CRUD;
import ua.com.foxminded.dao.mapper.TeacherMapper;
import ua.com.foxminded.domain.Teacher;

import java.util.List;

public class PostgreSqlTeacherDao implements CRUD<Teacher> {

    private final JdbcTemplate jdbcTemplate;

    public PostgreSqlTeacherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher create(Teacher teacher) {

        String sql = "INSERT INTO teachers (first_name, last_name, age) VALUES (?,?,?) RETURNING teachers.*;";
        return jdbcTemplate.query(sql, new TeacherMapper(),
            teacher.getFirstName(), teacher.getLastName(), teacher.getAge()).
            stream().findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public Teacher getById(long id) {

        String sql = "SELECT * FROM teachers WHERE id = ?;";
        return jdbcTemplate.query(sql, new TeacherMapper(), id)
            .stream().findAny().orElse(null);
    }

    @Override
    public List<Teacher> getAll() {

        String sql = "SELECT * FROM teachers;";
        return jdbcTemplate.query(sql, new TeacherMapper());
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM teachers WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
