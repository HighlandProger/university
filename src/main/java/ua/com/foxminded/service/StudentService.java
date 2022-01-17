package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class StudentService implements CrudService<Student> {

    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student create(Student student) {
        return studentDao.create(student);
    }

    @Override
    public Student getById(Long id) {
        return studentDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Student with id=" + id + " is not found"));
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }

    @Override
    public void delete(Long id) {
        studentDao.delete(id);
    }
}
