package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.Department;

@Service
public class DepartmentService extends AbstractService<Department> {

    public DepartmentService(AbstractDao<Department> abstractDao) {
        super(abstractDao);
    }
}
