package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Teacher;

/**
 * DAO class GroupDao
 */
@Repository
public class TeacherDao extends AbstractDao<Teacher> {

    public TeacherDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
