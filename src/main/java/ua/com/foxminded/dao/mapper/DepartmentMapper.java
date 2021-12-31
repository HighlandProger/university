package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.domain.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

        Department department = new Department();
        department.setId(rs.getLong("id"));
        department.setName(rs.getString("name"));

        return department;
    }
}
