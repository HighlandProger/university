package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.ClassRoom;

/**
 * DAO class ClassRoomDao
 */
@Repository
public class ClassRoomDao extends AbstractDao<ClassRoom> {

    public ClassRoomDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
