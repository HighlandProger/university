package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Student;

/**
 * DAO class GroupDao
 */
@Repository
public class StudentDao extends AbstractDao<Student> {

    public StudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
