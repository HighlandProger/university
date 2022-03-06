package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.Student;

@Service
public class StudentService extends AbstractService<Student> {

    public StudentService(AbstractDao<Student> abstractDao) {
        super(abstractDao);
    }
}
