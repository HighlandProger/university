package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.DepartmentDao;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    private final Department expectedDepartment = new Department(1L, "IT");
    private final long randomId = 5;

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentDao departmentDao;

    @Test
    void create_shouldCallDepartmentDaoCreate() {

        departmentService.create(expectedDepartment);

        verify(departmentDao).create(expectedDepartment);
    }

    @Test
    void getById_shouldCallDepartmentDaoGetById() {

        when(departmentDao.getById(expectedDepartment.getId())).thenReturn(Optional.of(expectedDepartment));

        departmentService.getById(expectedDepartment.getId());

        verify(departmentDao).getById(expectedDepartment.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(departmentDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> departmentService.getById(randomId));
        String expectedString = "Department with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallDepartmentDaoGetAll() {

        departmentService.getAll();

        verify(departmentDao).getAll();
    }

    @Test
    void delete_shouldCallDepartmentDaoDelete() {

        departmentService.delete(randomId);

        verify(departmentDao).delete(randomId);
    }

    @Test
    void  update_shouldCallDepartmentDaoUpdate() {

        departmentService.update(randomId, expectedDepartment);

        verify(departmentDao).update(randomId, expectedDepartment);
    }
}
