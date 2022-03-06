package ua.com.foxminded.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Group;

import javax.sql.DataSource;

@Repository
public class GroupDao extends AbstractDao<Group> {

    private static final String CREATE_SQL = "INSERT INTO groups (department_id, course_id, group_number) VALUES (?,?,?) RETURNING groups.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM groups WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM groups;";
    private static final String DELETE_SQL = "DELETE FROM groups WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE groups SET department_id = ?, course_id = ?, group_number = ? WHERE id = ?";

    public GroupDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object[] getParams(Group value) {
        Long departmentId = value.getDepartmentId();
        Long courseId = value.getCourseId();
        int groupNumber = value.getGroupNumber();
        return new Object[]{departmentId, courseId, groupNumber};
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
