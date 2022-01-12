package ua.com.foxminded.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.TeacherDao;
import ua.com.foxminded.domain.Teacher;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class TeacherService {

    private final Logger logger = LoggerFactory.getLogger(TeacherService.class.getName());
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher create(Teacher teacher) {

        logger.info("Call teacherDao.create()");
        return teacherDao.create(teacher);
    }

    public Teacher getById(Long id) {

        logger.info("Call teacherDao.getById({})", id);
        return teacherDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Teacher with id=" + id + " is not found"));
    }

    public List<Teacher> getAll() {

        logger.info("Call teacherDao.getAll()");
        return teacherDao.getAll();
    }

    public void delete(Long id) {

        logger.info("Call teacherDao.delete({})", id);
        teacherDao.delete(id);
    }
}
