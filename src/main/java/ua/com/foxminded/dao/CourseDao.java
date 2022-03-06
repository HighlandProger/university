package ua.com.foxminded.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Course;

import javax.sql.DataSource;

@Repository
public class CourseDao extends AbstractDao<Course> {

    private static final String CREATE_SQL = "INSERT INTO courses (establish_year) VALUES (?) RETURNING courses.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM courses WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM courses;";
    private static final String DELETE_SQL = "DELETE FROM courses WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE courses SET establish_year = ? WHERE id = ?";

    public CourseDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object[] getParams(Course value) {
        int establishYear = value.getEstablishYear();
        return new Object[]{establishYear};
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
