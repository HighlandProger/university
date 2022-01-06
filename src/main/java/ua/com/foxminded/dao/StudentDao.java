package ua.com.foxminded.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.com.foxminded.domain.Student;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class StudentDao implements CrudDao<Student> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student create(Student student) {

        String sql = "INSERT INTO students (group_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING students.*;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class),
            student.getGroupId(), student.getFirstName(), student.getLastName(), student.getAge());
    }

    @Override
    public Optional<Student> getById(long id) {

        String sql = "SELECT * FROM students WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), id));
    }

    @Override
    public List<Student> getAll() {

        String sql = "SELECT * FROM students;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void delete(long id) {

        String sql = "DELETE FROM students WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
