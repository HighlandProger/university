package ua.com.foxminded.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.ClassRoom;

import javax.sql.DataSource;

@Repository
public class ClassRoomDao extends AbstractDao<ClassRoom> {

    private static final String CREATE_SQL = "INSERT INTO classrooms (class_number) VALUES (?) RETURNING classrooms.*;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM classrooms WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT * FROM classrooms;";
    private static final String DELETE_SQL = "DELETE FROM classrooms WHERE id = ?;";
    private static final String UPDATE_SQL = "UPDATE classrooms SET class_number = ? WHERE id = ?";

    public ClassRoomDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object[] getParams(ClassRoom classRoom) {

        String classRoomNumberParam = classRoom.getClassNumber();
        return new Object[]{classRoomNumberParam};
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
