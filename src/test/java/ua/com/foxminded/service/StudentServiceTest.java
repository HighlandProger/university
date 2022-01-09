package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private final Student expectedStudent =
        new Student(1L, 2L, "Jack", "Johnson", 24);
    private final long randomId = 5;

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentDao studentDao;

    @Test
    void create_shouldCallStudentDaoCreate() {

        studentService.create(expectedStudent);

        verify(studentDao).create(expectedStudent);
    }

    @Test
    void getById_shouldCallStudentDaoGetById() {

        when(studentDao.getById(expectedStudent.getId())).thenReturn(Optional.of(expectedStudent));

        studentService.getById(expectedStudent.getId());

        verify(studentDao).getById(expectedStudent.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(studentDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> studentService.getById(randomId));
        String expectedString = "Student with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallStudentDaoGetAll() {

        studentService.getAll();

        verify(studentDao).getAll();
    }

    @Test
    void delete_shouldCallStudentDaoDelete() {

        studentService.delete(randomId);

        verify(studentDao).delete(randomId);
    }
}
