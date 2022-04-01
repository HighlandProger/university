package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.TeacherDao;
import ua.com.foxminded.exception.EntityNotFoundException;
import ua.com.foxminded.model.Teacher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    private final Teacher expectedTeacher =
        new Teacher(1L, "Nicola", "Tesla", 49, null);
    private final long randomId = 5;

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherDao teacherDao;

    @Test
    void create_shouldCallTeacherDaoCreate() {

        teacherService.create(expectedTeacher);

        verify(teacherDao).create(expectedTeacher);
    }

    @Test
    void getById_shouldCallTeacherDaoGetById() {

        when(teacherDao.getById(expectedTeacher.getId())).thenReturn(Optional.of(expectedTeacher));

        teacherService.getById(expectedTeacher.getId());

        verify(teacherDao).getById(expectedTeacher.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(teacherDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> teacherService.getById(randomId));
        String expectedString = "Object Teacher with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallTeacherDaoGetAll() {

        teacherService.getAll();

        verify(teacherDao).getAll();
    }

    @Test
    void delete_shouldCallTeacherDaoDelete() {

        teacherService.delete(randomId);

        verify(teacherDao).delete(randomId);
    }

    @Test
    void update_shouldCallTeacherDaoUpdate() {

        teacherService.update(expectedTeacher);

        verify(teacherDao).update(expectedTeacher);
    }
}
