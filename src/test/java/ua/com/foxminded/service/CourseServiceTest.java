package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    private final Course expectedCourse = new Course(1L, 2021);
    private final long randomId = 5;

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseDao courseDao;

    @Test
    void create_shouldCallCourseDaoCreate() {

        courseService.create(expectedCourse);

        verify(courseDao).create(expectedCourse);
    }

    @Test
    void getById_shouldCallCourseDaoGetById() {

        when(courseDao.getById(expectedCourse.getId())).thenReturn(Optional.of(expectedCourse));

        courseService.getById(expectedCourse.getId());

        verify(courseDao).getById(expectedCourse.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(courseDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> courseService.getById(randomId));
        String expectedString = "Course with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallCourseDaoGetAll() {

        courseService.getAll();

        verify(courseDao).getAll();
    }

    @Test
    void delete_shouldCallCourseDaoDelete() {

        courseService.delete(randomId);

        verify(courseDao).delete(randomId);
    }
}
