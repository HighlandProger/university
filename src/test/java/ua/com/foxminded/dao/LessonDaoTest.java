package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Lesson;
import ua.com.foxminded.domain.Teacher;
import ua.com.foxminded.util.DataSourceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LessonDaoTest {

    private final DriverManagerDataSource dataSource = DataSourceFactory.getInstance().initDataSource();
    private final SqlRunner sqlRunner = new SqlRunner(dataSource);
    private final LessonDao lessonDao = new LessonDao(dataSource);
    private Lesson expectedLesson;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateLesson() {

        assertEquals(0, lessonDao.getAll().size());
        Teacher teacher = new Teacher(1L, 2L, "John", "Travolta", 54);
        Group group = new Group(1L, 1L, 2L, 3);
        String lessonDate = "01.01.2022 15:30";

        expectedLesson = new Lesson(1L, "Algebra", teacher.getId(), group.getId(), lessonDate);
        Lesson actualLesson = lessonDao.create(expectedLesson);

        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    void getById_shouldReturnLesson() {

        assertEquals(0, lessonDao.getAll().size());
        Teacher teacher = new Teacher(1L, 2L, "John", "Travolta", 54);
        Group group = new Group(1L, 1L, 2L, 3);
        String lessonDate = "01.01.2022 15:30";
        Lesson lesson1 = lessonDao.create(new Lesson("Algebra", teacher.getId(), group.getId(), lessonDate));
        Lesson lesson2 = lessonDao.create(new Lesson("Geometry", teacher.getId(), group.getId(), lessonDate));
        Lesson lesson3 = lessonDao.create(new Lesson("Drawing", teacher.getId(), group.getId(), lessonDate));

        expectedLesson = lesson2;
        Optional<Lesson> actualLesson = lessonDao.getById(expectedLesson.getId());

        assertTrue(actualLesson.isPresent());
        assertEquals(expectedLesson, actualLesson.get());
    }

    @Test
    void getAll_shouldReturnAllLessons() {

        assertEquals(0, lessonDao.getAll().size());
        Teacher teacher = new Teacher(1L, 2L, "John", "Travolta", 54);
        Group group = new Group(1L, 1L, 2L, 3);
        String lessonDate = "01.01.2022 15:30";
        Lesson lesson1 = lessonDao.create(new Lesson("Algebra", teacher.getId(), group.getId(), lessonDate));
        Lesson lesson2 = lessonDao.create(new Lesson("Geometry", teacher.getId(), group.getId(), lessonDate));
        Lesson lesson3 = lessonDao.create(new Lesson("Drawing", teacher.getId(), group.getId(), lessonDate));

        List<Lesson> expectedLessons = Arrays.asList(lesson1, lesson2, lesson3);
        List<Lesson> actualLessons = lessonDao.getAll();

        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    void delete_shouldDeleteLesson() {

        assertEquals(0, lessonDao.getAll().size());
        String lessonDate = "01.01.2022 15:30";
        Lesson lesson = lessonDao.create(new Lesson(null, null, null, lessonDate));
        assertEquals(1, lessonDao.getAll().size());

        lessonDao.delete(lesson.getId());

        assertEquals(0, lessonDao.getAll().size());
    }
}
