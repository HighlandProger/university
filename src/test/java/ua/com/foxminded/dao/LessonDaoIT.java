package ua.com.foxminded.dao;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.model.*;
import ua.com.foxminded.utils.DateUtils;

import java.time.LocalDateTime;
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
class LessonDaoIT {

    private static final int GENERATED_LESSONS_COUNT = 18;
    private final Department randomDepartment = new Department(1L, "IT");
    private final Course randomCourse = new Course(1L, 2031);
    private final Teacher randomTeacher = new Teacher(1L, "Jack", "Nicolson", 54, randomDepartment);
    private final Group randomGroup = new Group(1L, randomDepartment, randomCourse, 0);
    private final LocalDateTime randomDateTime = DateUtils.getLocalDateTimeFromString("15.03.2022 12:44");
    private final ClassRoom randomClassRoom = new ClassRoom(1L, "1");

    @Autowired
    private LessonDao lessonDao;

    private Lesson expectedLesson;

    @Test
    void create_shouldCreateLesson() {

        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());

        expectedLesson = new Lesson( 19L, "Algebra", randomTeacher, randomGroup, randomDateTime, randomClassRoom);
        Lesson actualLesson = lessonDao.create(expectedLesson);

        System.out.println(expectedLesson);
        System.out.println(actualLesson);

        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    void getById_shouldReturnLesson() {

        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());

        Lesson lesson1 = lessonDao.create(new Lesson("Algebra", randomTeacher, randomGroup, randomDateTime, randomClassRoom));
        Lesson lesson2 = lessonDao.create(new Lesson("Geometry", randomTeacher, randomGroup, randomDateTime, randomClassRoom));
        Lesson lesson3 = lessonDao.create(new Lesson("Drawing", randomTeacher, randomGroup, randomDateTime, randomClassRoom));

        expectedLesson = lesson2;
        Optional<Lesson> actualLesson = lessonDao.getById(expectedLesson.getId());

        assertTrue(actualLesson.isPresent());
        assertEquals(expectedLesson, actualLesson.get());
    }

    @Test
    void getAll_shouldReturnAllLessons() {

        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());

        Lesson lesson1 = lessonDao.create(new Lesson("Algebra", randomTeacher, randomGroup, randomDateTime, randomClassRoom));
        Lesson lesson2 = lessonDao.create(new Lesson("Geometry", randomTeacher, randomGroup, randomDateTime, randomClassRoom));
        Lesson lesson3 = lessonDao.create(new Lesson("Drawing", randomTeacher, randomGroup, randomDateTime, randomClassRoom));

        List<Lesson> expectedLessons = Arrays.asList(lesson1, lesson2, lesson3);
        List<Lesson> actualLessons = lessonDao.getAll().stream().skip(GENERATED_LESSONS_COUNT).collect(Collectors.toList());

        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    void delete_shouldDeleteLesson() {

        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());

        Lesson lesson = lessonDao.create(new Lesson("Physics", randomTeacher, randomGroup, randomDateTime, randomClassRoom));
        assertEquals(GENERATED_LESSONS_COUNT + 1, lessonDao.getAll().size());

        lessonDao.delete(lesson.getId());

        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
    }

    @Test
    void update_shouldUpdateCourse() {

        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
        Lesson lesson = lessonDao.create(new Lesson("Math", randomTeacher, randomGroup, randomDateTime, randomClassRoom));
        assertEquals(GENERATED_LESSONS_COUNT + 1, lessonDao.getAll().size());

        Teacher changedRandomTeacher = new Teacher(5L, "Joshua", "Bloh", 50, new Department());
        Group changedRandomGroup = new Group(6L, new Department(), new Course(), 8);
        LocalDateTime changedRandomDateTime = DateUtils.getLocalDateTimeFromString("20.01.2010 14:42");
        ClassRoom changedRandomClassRoom = new ClassRoom(4L, "5");

        lesson.setTeacher(changedRandomTeacher);
        lesson.setGroup(changedRandomGroup);
        lesson.setDateTime(changedRandomDateTime);
        lesson.setClassRoom(changedRandomClassRoom);

        lessonDao.update(lesson);

        Optional<Lesson> updatedLesson = lessonDao.getById(lesson.getId());

        assertTrue(updatedLesson.isPresent());
        assertEquals(lesson, updatedLesson.get());
    }
}
