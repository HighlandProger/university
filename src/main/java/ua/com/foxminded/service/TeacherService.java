package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.Teacher;

@Service
public class TeacherService extends AbstractService<Teacher> {

    public TeacherService(AbstractDao<Teacher> abstractDao) {
        super(abstractDao);
    }
}
