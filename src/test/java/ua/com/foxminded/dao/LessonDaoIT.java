//package ua.com.foxminded.dao;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//import ua.com.foxminded.config.SpringDaoTestConfig;
//import ua.com.foxminded.model.ClassRoom;
//import ua.com.foxminded.model.Group;
//import ua.com.foxminded.model.Lesson;
//import ua.com.foxminded.model.Teacher;
//import ua.com.foxminded.utils.DateUtils;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = SpringDaoTestConfig.class)
//@Transactional
//@Rollback
//class LessonDaoIT {
//
//    private static final int GENERATED_LESSONS_COUNT = 18;
//
//    @Autowired
//    private LessonDao lessonDao;
//
//    private Lesson expectedLesson;
//
//    @Test
//    void create_shouldCreateLesson() {
//
//        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
//        Teacher teacher = new Teacher(1L, 2L, "John", "Travolta", 54);
//        Group group = new Group(1L, 1L, 2L, 3);
//        String lessonDate = "01.01.2022 15:30";
//        LocalDateTime dateTime = DateUtils.getLocalDateTimeFromString(lessonDate);
//        ClassRoom classRoom = new ClassRoom(1L, "1");
//
//        expectedLesson = new Lesson(1L, "Algebra", teacher.getId(), group.getId(), dateTime, classRoom.getId());
//        Lesson actualLesson = lessonDao.create(expectedLesson);
//
//        assertEquals(expectedLesson, actualLesson);
//    }
//
//    @Test
//    void getById_shouldReturnLesson() {
//
//        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
//        Teacher teacher = new Teacher(1L, 2L, "John", "Travolta", 54);
//        Group group = new Group(1L, 1L, 2L, 3);
//        String lessonDate = "01.01.2022 15:30";
//        LocalDateTime dateTime = DateUtils.getLocalDateTimeFromString(lessonDate);
//        ClassRoom classRoom = new ClassRoom(1L, "1");
//
//        Lesson lesson1 = lessonDao.create(new Lesson("Algebra", teacher.getId(), group.getId(), dateTime, classRoom.getId()));
//        Lesson lesson2 = lessonDao.create(new Lesson("Geometry", teacher.getId(), group.getId(), dateTime, classRoom.getId()));
//        Lesson lesson3 = lessonDao.create(new Lesson("Drawing", teacher.getId(), group.getId(), dateTime, classRoom.getId()));
//
//        expectedLesson = lesson2;
//        Optional<Lesson> actualLesson = lessonDao.getById(expectedLesson.getId());
//
//        assertTrue(actualLesson.isPresent());
//        assertEquals(expectedLesson, actualLesson.get());
//    }
//
//    @Test
//    void getAll_shouldReturnAllLessons() {
//
//        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
//        Teacher teacher = new Teacher(1L, 2L, "John", "Travolta", 54);
//        Group group = new Group(1L, 1L, 2L, 3);
//        String lessonDate = "01.01.2022 15:30";
//        LocalDateTime dateTime = DateUtils.getLocalDateTimeFromString(lessonDate);
//        ClassRoom classRoom = new ClassRoom(1L, "1");
//
//        Lesson lesson1 = lessonDao.create(new Lesson("Algebra", teacher.getId(), group.getId(), dateTime, classRoom.getId()));
//        Lesson lesson2 = lessonDao.create(new Lesson("Geometry", teacher.getId(), group.getId(), dateTime, classRoom.getId()));
//        Lesson lesson3 = lessonDao.create(new Lesson("Drawing", teacher.getId(), group.getId(), dateTime, classRoom.getId()));
//
//        List<Lesson> expectedLessons = Arrays.asList(lesson1, lesson2, lesson3);
//        List<Lesson> actualLessons = lessonDao.getAll().stream().skip(GENERATED_LESSONS_COUNT).collect(Collectors.toList());
//
//        assertEquals(expectedLessons, actualLessons);
//    }
//
//    @Test
//    void delete_shouldDeleteLesson() {
//
//        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
//        String lessonDate = "01.01.2022 15:30";
//        LocalDateTime dateTime = DateUtils.getLocalDateTimeFromString(lessonDate);
//        Lesson lesson = lessonDao.create(new Lesson(null, null, null, dateTime, null));
//        assertEquals(GENERATED_LESSONS_COUNT + 1, lessonDao.getAll().size());
//
//        lessonDao.delete(lesson.getId());
//
//        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
//    }
//
//    @Test
//    void update_shouldUpdateCourse() {
//
//        assertEquals(GENERATED_LESSONS_COUNT, lessonDao.getAll().size());
//        Lesson lesson1 = lessonDao.create(new Lesson("Math", 1L, 1L, DateUtils.getLocalDateTimeFromString("01.01.2021 11:00"), 1L));
//        Lesson lesson2 = new Lesson("Biology", 2L, 2L, DateUtils.getLocalDateTimeFromString("02.02.2022 22:00"), 1L);
//        assertEquals(GENERATED_LESSONS_COUNT + 1, lessonDao.getAll().size());
//
//        lessonDao.update(lesson1.getId(), lesson2);
//
//        Optional<Lesson> updatedLesson = lessonDao.getById(lesson1.getId());
//
//        assertTrue(updatedLesson.isPresent());
//        assertEquals(lesson1.getId(), updatedLesson.get().getId());
//        assertEquals(lesson2.getName(), updatedLesson.get().getName());
//        assertEquals(lesson2.getGroupId(), updatedLesson.get().getGroupId());
//        assertEquals(lesson2.getTeacherId(), updatedLesson.get().getTeacherId());
//        assertEquals(lesson2.getDateTime(), updatedLesson.get().getDateTime());
//    }
//}
