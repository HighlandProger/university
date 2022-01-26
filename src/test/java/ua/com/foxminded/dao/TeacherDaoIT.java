package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.model.Teacher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class TeacherDaoIT {

    @Autowired
    private TeacherDao teacherDao;
    private Teacher expectedTeacher;

    @Test
    void create_shouldCreateTeacher() {

        assertEquals(0, teacherDao.getAll().size());
        Department department = new Department(1L, "IT");

        expectedTeacher = new Teacher(1L, department.getId(), "Antony", "Hopkins", 58);
        Teacher actualTeacher = teacherDao.create(expectedTeacher);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void getById_shouldReturnTeacher() {

        assertEquals(0, teacherDao.getAll().size());
        Teacher teacher1 = teacherDao.create(new Teacher("John", "Week", 48));
        Teacher teacher2 = teacherDao.create(new Teacher("Stan", "Lee", 67));
        Teacher teacher3 = teacherDao.create(new Teacher("Jim", "Kerry", 58));

        expectedTeacher = teacher2;
        Optional<Teacher> actualTeacher = teacherDao.getById(expectedTeacher.getId());

        assertTrue(actualTeacher.isPresent());
        assertEquals(expectedTeacher, actualTeacher.get());
    }

    @Test
    void getAll_shouldReturnAllTeachers() {

        assertEquals(0, teacherDao.getAll().size());
        Teacher teacher1 = teacherDao.create(new Teacher("John", "Week", 48));
        Teacher teacher2 = teacherDao.create(new Teacher("Stan", "Lee", 67));
        Teacher teacher3 = teacherDao.create(new Teacher("Jim", "Kerry", 58));

        List<Teacher> expectedTeachers = Arrays.asList(teacher1, teacher2, teacher3);
        List<Teacher> actualTeachers = teacherDao.getAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void delete_shouldDeleteTeacher() {

        assertEquals(0, teacherDao.getAll().size());
        Teacher teacher = teacherDao.create(new Teacher("John", "Week", 48));
        assertEquals(1, teacherDao.getAll().size());

        teacherDao.delete(teacher.getId());

        assertEquals(0, teacherDao.getAll().size());
    }

    @Test
    void update_shouldUpdateTeacher() {

        assertEquals(0, teacherDao.getAll().size());
        Teacher teacher1 = teacherDao.create(new Teacher("Tom", "Thompson", 44, 4L));
        Teacher teacher2 = new Teacher("Paul", "Paulson", 55, 5L);
        assertEquals(1, teacherDao.getAll().size());

        teacherDao.update(teacher1.getId(), teacher2);

        Optional<Teacher> updatedTeacher = teacherDao.getById(teacher1.getId());

        assertTrue(updatedTeacher.isPresent());
        assertEquals(teacher1.getId(), updatedTeacher.get().getId());
        assertEquals(teacher2.getFirstName(), updatedTeacher.get().getFirstName());
        assertEquals(teacher2.getLastName(), updatedTeacher.get().getLastName());
        assertEquals(teacher2.getAge(), updatedTeacher.get().getAge());
        assertEquals(teacher2.getDepartmentId(), updatedTeacher.get().getDepartmentId());
    }
}