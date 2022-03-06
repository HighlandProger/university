package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.ClassRoom;

@Service
public class ClassRoomService extends AbstractService<ClassRoom> {

    public ClassRoomService(AbstractDao<ClassRoom> abstractDao) {
        super(abstractDao);
    }
}
