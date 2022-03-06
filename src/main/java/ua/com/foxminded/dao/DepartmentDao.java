package ua.com.foxminded.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Department;

import javax.sql.DataSource;

@Repository
public class DepartmentDao extends AbstractDao<Department> {

    private static final String CREATE_SQL = "INSERT INTO departments (name) VALUES (?) RETURNING departments.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM departments WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM departments;";
    private static final String DELETE_SQL = "DELETE FROM departments WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE departments SET name = ? WHERE id = ?";

    public DepartmentDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object[] getParams(Department value) {
        String departmentName = value.getName();
        return new Object[]{departmentName};
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
