package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.EntityNotFoundException;

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
        return studentDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Student with id=" + id + " is not found"));
    }

    public List<Student> getAll() {
        return studentDao.getAll();
    }

    public void delete(Long id) {
        studentDao.delete(id);
    }
}
