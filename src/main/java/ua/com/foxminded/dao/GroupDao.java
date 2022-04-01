package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Group;

/**
 * DAO class GroupDao
 */
@Repository
public class GroupDao extends AbstractDao<Group> {

    public GroupDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
