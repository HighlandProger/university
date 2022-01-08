package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.TeacherDao;
import ua.com.foxminded.domain.Teacher;
import ua.com.foxminded.exception.ServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher create(Teacher teacher) {
        return teacherDao.create(teacher);
    }

    public Teacher getById(Long id) {

        Optional<Teacher> teacher = teacherDao.getById(id);
        if (!teacher.isPresent()) {
            throw new ServiceException("Teacher with id=" + id + " is not found");
        }
        return teacher.get();
    }

    public List<Teacher> getAll() {
        return teacherDao.getAll();
    }

    public void delete(Long id) {
        teacherDao.delete(id);
    }
}
