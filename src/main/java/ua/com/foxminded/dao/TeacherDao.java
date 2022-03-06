package ua.com.foxminded.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Teacher;

import javax.sql.DataSource;

@Repository
public class TeacherDao extends AbstractDao<Teacher> {

    private static final String CREATE_SQL = "INSERT INTO teachers (department_id, first_name, last_name, age) VALUES (?,?,?,?) RETURNING teachers.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM teachers WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM teachers;";
    private static final String DELETE_SQL = "DELETE FROM teachers WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE teachers SET department_id = ?, first_name = ?, last_name = ?, age =? WHERE id = ?";

    public TeacherDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object[] getParams(Teacher value) {
        Long departmentId = value.getDepartmentId();
        String firstName = value.getFirstName();
        String lastName = value.getLastName();
        int age = value.getAge();
        return new Object[]{departmentId, firstName, lastName, age};
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
