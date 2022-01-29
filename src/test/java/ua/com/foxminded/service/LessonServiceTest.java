package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.LessonDao;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.exception.EntityNotFoundException;
import ua.com.foxminded.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    private final Lesson expectedLesson =
        new Lesson(1L, "Algebra", 1L, 2L, DateUtils.getLocalDateTimeFromString("05.01.2022 13:00"));
    private final long randomId = 5;

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonDao lessonDao;

    @Test
    void create_shouldCallLessonDaoCreate() {

        lessonService.create(expectedLesson);

        verify(lessonDao).create(expectedLesson);
    }

    @Test
    void getById_shouldCallLessonDaoGetById() {

        when(lessonDao.getById(expectedLesson.getId())).thenReturn(Optional.of(expectedLesson));

        lessonService.getById(expectedLesson.getId());

        verify(lessonDao).getById(expectedLesson.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(lessonDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> lessonService.getById(randomId));
        String expectedString = "Lesson with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallLessonDaoGetAll() {

        lessonService.getAll();

        verify(lessonDao).getAll();
    }

    @Test
    void delete_shouldCallLessonDaoDelete() {

        lessonService.delete(randomId);

        verify(lessonDao).delete(randomId);
    }

    @Test
    void  update_shouldCallLessonDaoUpdate() {

        lessonService.update(randomId, expectedLesson);

        verify(lessonDao).update(randomId, expectedLesson);
    }
}
