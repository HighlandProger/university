package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Lesson;

/**
 * DAO class GroupDao
 */
@Repository
public class LessonDao extends AbstractDao<Lesson> {

    public LessonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
