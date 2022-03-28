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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class DepartmentDaoIT {

    private static final int GENERATED_DEPARTMENTS_COUNT = 3;

    @Autowired
    private DepartmentDao departmentDao;

    private Department expectedDepartment;

    @Test
    void create_shouldCreateDepartment() {

        assertEquals(GENERATED_DEPARTMENTS_COUNT, departmentDao.getAll().size());

        expectedDepartment = new Department(1L, "IT");
        Department actualDepartment = departmentDao.create(expectedDepartment);

        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    void getById_shouldReturnDepartment() {

        assertEquals(GENERATED_DEPARTMENTS_COUNT, departmentDao.getAll().size());
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

        assertEquals(GENERATED_DEPARTMENTS_COUNT, departmentDao.getAll().size());
        Department department1 = departmentDao.create(new Department("IT"));
        Department department2 = departmentDao.create(new Department("History"));
        Department department3 = departmentDao.create(new Department("Gelology"));

        List<Department> expectedDepartments = Arrays.asList(department1, department2, department3);
        List<Department> actualDepartments = departmentDao.getAll().stream().skip(GENERATED_DEPARTMENTS_COUNT).collect(Collectors.toList());

        assertEquals(expectedDepartments, actualDepartments);
    }

    @Test
    void delete_shouldDeleteDepartment() {

        assertEquals(GENERATED_DEPARTMENTS_COUNT, departmentDao.getAll().size());
        Department department = departmentDao.create(new Department("IT"));
        assertEquals(GENERATED_DEPARTMENTS_COUNT + 1, departmentDao.getAll().size());

        departmentDao.delete(department.getId());

        assertEquals(GENERATED_DEPARTMENTS_COUNT, departmentDao.getAll().size());
    }

    @Test
    void update_shouldUpdateDepartment() {

        assertEquals(GENERATED_DEPARTMENTS_COUNT, departmentDao.getAll().size());
        Department department = departmentDao.create(new Department("IT"));
        String randomDepartmentName = "Economics";
        assertEquals(GENERATED_DEPARTMENTS_COUNT + 1, departmentDao.getAll().size());

        department.setName(randomDepartmentName);
        departmentDao.update(department);

        Optional<Department> updatedDepartment = departmentDao.getById(department.getId());

        assertTrue(updatedDepartment.isPresent());
        assertEquals(updatedDepartment.get(), department);
    }
}
