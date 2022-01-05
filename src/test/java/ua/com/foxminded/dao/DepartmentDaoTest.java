package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.domain.Department;
import ua.com.foxminded.util.DataSourceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepartmentDaoTest {

    private final DriverManagerDataSource dataSource = DataSourceFactory.getInstance().initDataSource();
    private final SqlRunner sqlRunner = new SqlRunner(dataSource);
    private final DepartmentDao departmentDao = new DepartmentDao(dataSource);
    private Department expectedDepartment;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateDepartment() {

        assertEquals(0, departmentDao.getAll().size());

        expectedDepartment = new Department(1L, "IT");
        Department actualDepartment = departmentDao.create(expectedDepartment);

        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    void getById_shouldReturnDepartment() {

        assertEquals(0, departmentDao.getAll().size());
        Department department1 = departmentDao.create(new Department("IT"));
        Department department2 = departmentDao.create(new Department("History"));
        Department department3 = departmentDao.create(new Department("Gelology"));

        expectedDepartment = department2;
        Optional<Department> actualDepartment = departmentDao.getById(expectedDepartment.getId());

        assertTrue(actualDepartment.isPresent());
        assertEquals(expectedDepartment, actualDepartment.get());
    }

    @Test
    void getAll_shouldReturnAllDepartments() {

        assertEquals(0, departmentDao.getAll().size());
        Department department1 = departmentDao.create(new Department("IT"));
        Department department2 = departmentDao.create(new Department("History"));
        Department department3 = departmentDao.create(new Department("Gelology"));

        List<Department> expectedDepartments = Arrays.asList(department1, department2, department3);
        List<Department> actualDepartments = departmentDao.getAll();

        assertEquals(expectedDepartments, actualDepartments);
    }

    @Test
    void delete_shouldDeleteDepartment() {

        assertEquals(0, departmentDao.getAll().size());
        Department department = departmentDao.create(new Department("IT"));
        assertEquals(1, departmentDao.getAll().size());

        departmentDao.delete(department.getId());

        assertEquals(0, departmentDao.getAll().size());
    }
}
