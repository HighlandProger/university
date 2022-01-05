package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.util.DataSourceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseDaoTest {

    private final DriverManagerDataSource dataSource = DataSourceFactory.getInstance().initDataSource();
    private final SqlRunner sqlRunner = new SqlRunner(dataSource);
    private final CourseDao courseDao = new CourseDao(dataSource);
    private Course expectedCourse;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

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
