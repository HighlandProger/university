package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.Course;

/**
 * Service class CourseService - mediator between Course and CourseDao
 */
@Service
public class CourseService extends AbstractService<Course> {

    public CourseService(AbstractDao<Course> abstractDao) {
        super(abstractDao);
    }
}
