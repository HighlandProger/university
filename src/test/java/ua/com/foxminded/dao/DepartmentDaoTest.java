package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.domain.Department;
import ua.com.foxminded.util.DataSourceFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentDaoTest {

    private final DriverManagerDataSource dataSource = DataSourceFactory.getInstance().initDataSource();
    private final SqlRunner sqlRunner = new SqlRunner(dataSource);
    private final DepartmentDao departmentDao = new DepartmentDao(dataSource);
    private Department expectedDepartment;
    private Department actualDepartment;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateDepartment() {

        assertEquals(0, departmentDao.getAll().size());

        expectedDepartment = new Department(1, "IT");
        actualDepartment = departmentDao.create(expectedDepartment);

        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    void getById_shouldReturnDepartment() {

        assertEquals(0, departmentDao.getAll().size());
        Department department1 = departmentDao.create(new Department(1, "IT"));
        Department department2 = departmentDao.create(new Department(2, "History"));
        Department department3 = departmentDao.create(new Department(3, "Gelology"));

        expectedDepartment = department2;
        departmentDao.getById(expectedDepartment.getId()).ifPresent(department -> actualDepartment = department);

        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    void getAll_shouldReturnAllDepartments() {

        assertEquals(0, departmentDao.getAll().size());
        Department department1 = departmentDao.create(new Department(1, "IT"));
        Department department2 = departmentDao.create(new Department(2, "History"));
        Department department3 = departmentDao.create(new Department(3, "Gelology"));

        List<Department> expectedDepartments = Arrays.asList(department1, department2, department3);
        List<Department> actualDepartments = departmentDao.getAll();

        assertEquals(expectedDepartments, actualDepartments);
    }

    @Test
    void delete_shouldDeleteDepartment() {

        assertEquals(0, departmentDao.getAll().size());
        Department department = departmentDao.create(new Department(1, "IT"));
        assertEquals(1, departmentDao.getAll().size());

        departmentDao.delete(department.getId());

        assertEquals(0, departmentDao.getAll().size());
    }
}
