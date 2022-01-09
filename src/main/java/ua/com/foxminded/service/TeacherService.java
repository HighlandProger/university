package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.TeacherDao;
import ua.com.foxminded.domain.Teacher;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

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
        return teacherDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Teacher with id=" + id + " is not found"));
    }

    public List<Teacher> getAll() {
        return teacherDao.getAll();
    }

    public void delete(Long id) {
        teacherDao.delete(id);
    }
}
