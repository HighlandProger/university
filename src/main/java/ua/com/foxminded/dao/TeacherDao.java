package ua.com.foxminded.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.dao.mapper.TeacherMapper;
import ua.com.foxminded.domain.Teacher;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class TeacherDao implements CrudDao<Teacher> {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher create(Teacher teacher) {

        String sql = "INSERT INTO teachers (department_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING teachers.*;";
        return jdbcTemplate.queryForObject(sql, new TeacherMapper(),
            teacher.getDepartmentId(), teacher.getFirstName(), teacher.getLastName(), teacher.getAge());
    }

    @Override
    public Optional<Teacher> getById(long id) {

        String sql = "SELECT * FROM teachers WHERE id = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new TeacherMapper(), id));
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
