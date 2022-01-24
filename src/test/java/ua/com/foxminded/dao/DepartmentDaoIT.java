package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.model.Department;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class DepartmentDaoIT {

    @Autowired
    private DepartmentDao departmentDao;

    private Department expectedDepartment;

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

    @Test
    void update_shouldUpdateDepartment() {

        assertEquals(0, departmentDao.getAll().size());
        Department department1 = departmentDao.create(new Department("IT"));
        Department department2 = new Department("Geography");
        assertEquals(1, departmentDao.getAll().size());

        departmentDao.update(department1.getId(), department2);

        Optional<Department> updatedDepartment = departmentDao.getById(department1.getId());

        assertTrue(updatedDepartment.isPresent());
        assertEquals(department1.getId(), updatedDepartment.get().getId());
        assertEquals(department2.getName(), updatedDepartment.get().getName());
    }
}
