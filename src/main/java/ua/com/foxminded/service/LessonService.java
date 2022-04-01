package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.Lesson;

/**
 * Service class LessonService - mediator between Lesson and LessonDao
 */
@Service
public class LessonService extends AbstractService<Lesson> {

    public LessonService(AbstractDao<Lesson> abstractDao) {
        super(abstractDao);
    }
}
