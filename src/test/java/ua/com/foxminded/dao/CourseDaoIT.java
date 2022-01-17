package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.domain.Course;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class CourseDaoIT {

    @Autowired
    private CourseDao courseDao;

    private Course expectedCourse;

    @Test
    void create_shouldCreateCourse() {

        assertEquals(0, courseDao.getAll().size());

        expectedCourse = new Course(1L, 2021);
        Course actualCourse = courseDao.create(expectedCourse);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getById_shouldReturnCourse() {

        assertEquals(0, courseDao.getAll().size());
        Course course1 = courseDao.create(new Course(2021));
        Course course2 = courseDao.create(new Course(2022));
        Course course3 = courseDao.create(new Course(2023));

        expectedCourse = course2;
        Optional<Course> actualCourse = courseDao.getById(expectedCourse.getId());

        assertTrue(actualCourse.isPresent());
        assertEquals(expectedCourse, actualCourse.get());
    }

    @Test
    void getAll_shouldReturnAllCourses() {

        assertEquals(0, courseDao.getAll().size());
        Course course1 = courseDao.create(new Course(2021));
        Course course2 = courseDao.create(new Course(2022));
        Course course3 = courseDao.create(new Course(2023));

        List<Course> expectedCourses = Arrays.asList(course1, course2, course3);
        List<Course> actualCourses = courseDao.getAll();

        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    void delete_shouldDeleteCourse() {

        assertEquals(0, courseDao.getAll().size());
        Course course = courseDao.create(new Course(2021));
        assertEquals(1, courseDao.getAll().size());

        courseDao.delete(course.getId());

        assertEquals(0, courseDao.getAll().size());
    }
}
