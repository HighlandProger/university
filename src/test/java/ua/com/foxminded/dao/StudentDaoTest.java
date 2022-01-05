package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.util.DataSourceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentDaoTest {

    private final DriverManagerDataSource dataSource = DataSourceFactory.getInstance().initDataSource();
    private final SqlRunner sqlRunner = new SqlRunner(dataSource);
    private final StudentDao studentDao = new StudentDao(dataSource);
    private Student expectedStudent;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateStudent() {

        assertEquals(0, studentDao.getAll().size());
        Group group = new Group(1L,1L, 1L, 2);

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
}
