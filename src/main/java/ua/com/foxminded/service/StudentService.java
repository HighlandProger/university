package ua.com.foxminded.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class.getName());
    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student create(Student student) {

        logger.info("Call studentDao.create()");
        return studentDao.create(student);
    }

    public Student getById(Long id) {

        logger.info("Call studentDao.getById({})", id);
        return studentDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Student with id=" + id + " is not found"));
    }

    public List<Student> getAll() {

        logger.info("Call studentDao.getAll()");
        return studentDao.getAll();
    }

    public void delete(Long id) {

        logger.info("Call studentDao.delete({})", id);
        studentDao.delete(id);
    }
}
