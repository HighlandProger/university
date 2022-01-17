package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.LessonDao;
import ua.com.foxminded.domain.Lesson;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class LessonService implements CrudService<Lesson> {

    private final LessonDao lessonDao;

    @Autowired
    public LessonService(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    @Override
    public Lesson create(Lesson group) {
        return lessonDao.create(group);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Lesson with id=" + id + " is not found"));
    }

    @Override
    public List<Lesson> getAll() {
        return lessonDao.getAll();
    }

    @Override
    public void delete(Long id) {
        lessonDao.delete(id);
    }
}
