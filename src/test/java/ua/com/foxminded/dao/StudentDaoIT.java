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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class StudentDaoIT {

    @Autowired
    private StudentDao studentDao;

    private Student expectedStudent;

    @Test
    void create_shouldCreateStudent() {

        assertEquals(0, studentDao.getAll().size());
        Group group = new Group(1L, 1L, 1L, 2);

        expectedStudent = new Student(1L, group.getId(), "Tom", "Holland", 24);
        Student actualStudent = studentDao.create(expectedStudent);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getById_shouldReturnStudent() {

        assertEquals(0, studentDao.getAll().size());
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

        assertEquals(0, studentDao.getAll().size());
        Student student1 = studentDao.create(new Student("Justin", "Timberlake", 32));
        Student student2 = studentDao.create(new Student("Bob", "Marley", 40));
        Student student3 = studentDao.create(new Student("Tom", "Hanks", 48));

        List<Student> expectedStudents = Arrays.asList(student1, student2, student3);
        List<Student> actualStudents = studentDao.getAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void delete_shouldDeleteStudent() {

        assertEquals(0, studentDao.getAll().size());
        Student student = studentDao.create(new Student("Bob", "Marley", 40));
        assertEquals(1, studentDao.getAll().size());

        studentDao.delete(student.getId());

        assertEquals(0, studentDao.getAll().size());
    }

    @Test
    void update_shouldUpdateStudent() {

        assertEquals(0, studentDao.getAll().size());
        Student student1 = studentDao.create(new Student("John", "Johnson", 11, 1L));
        Student student2 = new Student("Nick", "Nickolson", 22, 2L);
        assertEquals(1, studentDao.getAll().size());

        studentDao.update(student1.getId(), student2);

        Optional<Student> updatedStudent = studentDao.getById(student1.getId());

        assertTrue(updatedStudent.isPresent());
        assertEquals(student1.getId(), updatedStudent.get().getId());
        assertEquals(student2.getFirstName(), updatedStudent.get().getFirstName());
        assertEquals(student2.getLastName(), updatedStudent.get().getLastName());
        assertEquals(student2.getAge(), updatedStudent.get().getAge());
        assertEquals(student2.getGroupId(), updatedStudent.get().getGroupId());
    }
}
