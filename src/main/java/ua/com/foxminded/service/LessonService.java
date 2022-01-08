package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.LessonDao;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Lesson;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonDao lessonDao;

    @Autowired
    public LessonService(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public Lesson create(Lesson group) {
        return lessonDao.create(group);
    }

    public Lesson getById(Long id) {
        return lessonDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Lesson with id=" + id + " is not found"));
    }

    public List<Lesson> getAll() {
        return lessonDao.getAll();
    }

    public void delete(Long id) {
        lessonDao.delete(id);
    }
}
