package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.ServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student create(Student student) {
        return studentDao.create(student);
    }

    public Student getById(Long id) {

        Optional<Student> student = studentDao.getById(id);
        if (!student.isPresent()) {
            throw new ServiceException("Student with id=" + id + " is not found");
        }
        return student.get();
    }

    public List<Student> getAll() {
        return studentDao.getAll();
    }

    public void delete(Long id) {
        studentDao.delete(id);
    }
}
