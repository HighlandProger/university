package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;

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
class StudentDaoIT {

    private static final int GENERATED_STUDENTS_COUNT = 3;
    private final Group randomGroup = new Group(8L, null, null, 3);

    @Autowired
    private StudentDao studentDao;

    private Student expectedStudent;

    @Test
    void create_shouldCreateStudent() {

        assertEquals(GENERATED_STUDENTS_COUNT, studentDao.getAll().size());
        Group group = new Group(1L, null, null, 2);

        expectedStudent = new Student(7L,"Tom", "Holland", 23, group);
        Student actualStudent = studentDao.create(expectedStudent);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById_shouldReturnStudent() {

        assertEquals(GENERATED_STUDENTS_COUNT, studentDao.getAll().size());
        Student student1 = studentDao.create(new Student("Joe", "Frazer", 25));
        Student student2 = studentDao.create(new Student("Nicole", "Kidman", 29));
        Student student3 = studentDao.create(new Student("Tom", "Cruz", 34));

        expectedStudent = student2;
        Optional<Student> actualStudent = studentDao.getById(expectedStudent.getId());

        assertTrue(actualStudent.isPresent());
        assertEquals(expectedStudent, actualStudent.get());
    }

    @Test
    void getAll_shouldReturnAllStudents() {

        assertEquals(GENERATED_STUDENTS_COUNT, studentDao.getAll().size());
        Student student1 = studentDao.create(new Student("Justin", "Timberlake", 32, randomGroup));
        Student student2 = studentDao.create(new Student("Bob", "Marley", 40, randomGroup));
        Student student3 = studentDao.create(new Student("Tom", "Hanks", 48, randomGroup));

        List<Student> expectedStudents = Arrays.asList(student1, student2, student3);
        List<Student> actualStudents = studentDao.getAll().stream().skip(GENERATED_STUDENTS_COUNT).collect(Collectors.toList());

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void delete_shouldDeleteStudent() {

        assertEquals(GENERATED_STUDENTS_COUNT, studentDao.getAll().size());
        Student student = studentDao.create(new Student("Bob", "Marley", 40));
        assertEquals(GENERATED_STUDENTS_COUNT + 1, studentDao.getAll().size());

        studentDao.delete(student.getId());

        assertEquals(GENERATED_STUDENTS_COUNT, studentDao.getAll().size());
    }

    @Test
    void update_shouldUpdateStudent() {

        assertEquals(GENERATED_STUDENTS_COUNT, studentDao.getAll().size());
        Student student = studentDao.create(new Student("John", "Johnson", 11, randomGroup));
        assertEquals(GENERATED_STUDENTS_COUNT + 1, studentDao.getAll().size());

        String randomFirstName = "Jack";
        String randomLastName = "Jackson";
        int randomAge = 23;
        int randomNumber = 3;

        Group changedRandomGroup = randomGroup;
        changedRandomGroup.setId(randomGroup.getId() + randomNumber);

        student.setFirstName(randomFirstName);
        student.setLastName(randomLastName);
        student.setAge(randomAge);
        student.setGroup(changedRandomGroup);
        studentDao.update(student);

        Optional<Student> updatedStudent = studentDao.getById(student.getId());

        assertTrue(updatedStudent.isPresent());
        assertEquals(updatedStudent.get(), student);
    }
}
