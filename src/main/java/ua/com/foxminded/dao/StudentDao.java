package ua.com.foxminded.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Student;

import javax.sql.DataSource;

@Repository
public class StudentDao extends AbstractDao<Student> {

    private static final String CREATE_SQL = "INSERT INTO students (group_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING students.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM students WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM students;";
    private static final String DELETE_SQL = "DELETE FROM students WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ?, age =? WHERE id = ?";

    public StudentDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object[] getParams(Student value) {
        Long groupId = value.getGroupId();
        String firstName = value.getFirstName();
        String lastName = value.getLastName();
        int age = value.getAge();
        return new Object[]{groupId, firstName, lastName, age};
    }

    @Override
    protected String getCreateObjectSql() {
        return CREATE_SQL;
    }

    @Override
    protected String getObjectByIdSql() {
        return GET_BY_ID_SQL;
    }

    @Override
    protected String getAllObjectsSql() {
        return GET_ALL_SQL;
    }

    @Override
    protected String getDeleteObjectSql() {
        return DELETE_SQL;
    }

    @Override
    protected String getUpdateObjectSql() {
        return UPDATE_SQL;
    }
}
