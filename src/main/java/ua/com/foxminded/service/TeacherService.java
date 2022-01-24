package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.TeacherDao;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class TeacherService implements CrudService<Teacher> {

    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public Teacher create(Teacher teacher) {
        return teacherDao.create(teacher);
    }

    @Override
    public Teacher getById(Long id) {
        return teacherDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Teacher with id=" + id + " is not found"));
    }

    @Override
    public List<Teacher> getAll() {
        return teacherDao.getAll();
    }

    @Override
    public void delete(Long id) {
        teacherDao.delete(id);
    }

    @Override
    public void update(Long id, Teacher teacher) {
        teacherDao.update(id, teacher);
    }
}
