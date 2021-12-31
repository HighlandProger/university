package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.mapper.StudentMapper;
import ua.com.foxminded.domain.Student;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class StudentDao implements CrudDao<Student> {

    private final JdbcTemplate jdbcTemplate;

    public StudentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student create(Student student) {

        String sql = "INSERT INTO students (first_name, last_name, age) VALUES (?,?,?) RETURNING students.*;";
        return jdbcTemplate.queryForObject(sql, new StudentMapper(),
            student.getFirstName(), student.getLastName(), student.getAge());
    }

    @Override
    public Optional<Student> getById(long id) {

        String sql = "SELECT * FROM students WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentMapper(), id));
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
