package ua.com.foxminded.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.model.Department;

/**
 * DAO class DepartmentDao
 */
@Repository
public class DepartmentDao extends AbstractDao<Department> {

    public DepartmentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
