package ua.com.foxminded.dao.postgresql;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.CRUD;
import ua.com.foxminded.dao.mapper.StudentMapper;
import ua.com.foxminded.domain.Student;

import java.util.List;

public class PostgreSqlStudentDao implements CRUD<Student> {

    private final JdbcTemplate jdbcTemplate;

    public PostgreSqlStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student create(Student student) {

        String sql = "INSERT INTO students (first_name, last_name, age) VALUES (?,?,?) RETURNING students.*;";
        return jdbcTemplate.query(sql, new StudentMapper(),
            student.getFirstName(), student.getLastName(), student.getAge()).
            stream().findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public Student getById(long id) {

        String sql = "SELECT * FROM students WHERE id = ?;";
        return jdbcTemplate.query(sql, new StudentMapper(), id)
            .stream().findAny().orElse(null);
    }

    @Override
    public List<Student> getAll() {

        String sql = "SELECT * FROM students;";
        return jdbcTemplate.query(sql, new StudentMapper());
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM students WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
