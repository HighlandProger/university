package ua.com.foxminded.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.LessonDao;
import ua.com.foxminded.domain.Lesson;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class LessonService {

    private final Logger logger = LoggerFactory.getLogger(LessonService.class.getName());
    private final LessonDao lessonDao;

    @Autowired
    public LessonService(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public Lesson create(Lesson group) {

        logger.info("Call lessonDao.create()");
        return lessonDao.create(group);
    }

    public Lesson getById(Long id) {

        logger.info("Call lessonDao.getById({})", id);
        return lessonDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Lesson with id=" + id + " is not found"));
    }

    public List<Lesson> getAll() {

        logger.info("Call lessonDao.getAll()");
        return lessonDao.getAll();
    }

    public void delete(Long id) {

        logger.info("Call lessonDao.delete({})", id);
        lessonDao.delete(id);
    }
}
