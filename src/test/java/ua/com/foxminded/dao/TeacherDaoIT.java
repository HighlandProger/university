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
import ua.com.foxminded.model.Teacher;

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
class TeacherDaoIT {

    private static final int GENERATED_TEACHERS_COUNT = 3;
    private final Department randomDepartment = new Department(9L, "Geography");

    @Autowired
    private TeacherDao teacherDao;

    private Teacher expectedTeacher;

    @Test
    void create_shouldCreateTeacher() {

        assertEquals(GENERATED_TEACHERS_COUNT, teacherDao.getAll().size());
        Department department = new Department(1L, "IT");

        expectedTeacher = new Teacher(1L, "Antony", "Hopkins", 58, department);
        Teacher actualTeacher = teacherDao.create(expectedTeacher);

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    void getById_shouldReturnTeacher() {

        assertEquals(GENERATED_TEACHERS_COUNT, teacherDao.getAll().size());
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

        assertEquals(GENERATED_TEACHERS_COUNT, teacherDao.getAll().size());
        Teacher teacher1 = teacherDao.create(new Teacher("John", "Week", 48));
        Teacher teacher2 = teacherDao.create(new Teacher("Stan", "Lee", 67));
        Teacher teacher3 = teacherDao.create(new Teacher("Jim", "Kerry", 58));

        List<Teacher> expectedTeachers = Arrays.asList(teacher1, teacher2, teacher3);
        List<Teacher> actualTeachers = teacherDao.getAll().stream().skip(GENERATED_TEACHERS_COUNT).collect(Collectors.toList());

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    void delete_shouldDeleteTeacher() {

        assertEquals(GENERATED_TEACHERS_COUNT, teacherDao.getAll().size());
        Teacher teacher = teacherDao.create(new Teacher("John", "Week", 48));
        assertEquals(GENERATED_TEACHERS_COUNT + 1, teacherDao.getAll().size());

        teacherDao.delete(teacher.getId());

        assertEquals(GENERATED_TEACHERS_COUNT, teacherDao.getAll().size());
    }

    @Test
    void update_shouldUpdateTeacher() {

        assertEquals(GENERATED_TEACHERS_COUNT, teacherDao.getAll().size());
        Teacher teacher = teacherDao.create(new Teacher("Tom", "Thompson", 44, new Department(4L, "IT")));
        assertEquals(GENERATED_TEACHERS_COUNT + 1, teacherDao.getAll().size());

        String randomFirstName = "Bob";
        String randomLastName = "Williams";
        int randomAge = 43;
        int randomNumber = 6;

        Department changedRandomDepartment = randomDepartment;
        changedRandomDepartment.setId(randomDepartment.getId() + randomNumber);

        teacher.setFirstName(randomFirstName);
        teacher.setLastName(randomLastName);
        teacher.setAge(randomAge);
        teacher.setDepartment(changedRandomDepartment);

        teacherDao.update(teacher);

        Optional<Teacher> updatedTeacher = teacherDao.getById(teacher.getId());

        assertTrue(updatedTeacher.isPresent());
        assertEquals(updatedTeacher.get(), teacher);
    }
}
