package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Course;

/**
 * DAO class CourseDao
 */
@Repository
public class CourseDao extends AbstractDao<Course> {

    public CourseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
