package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.AbstractDao;
import ua.com.foxminded.model.Group;

/**
 * Service class GroupService - mediator between Group and GroupDao
 */
@Service
public class GroupService extends AbstractService<Group> {

    public GroupService(AbstractDao<Group> abstractDao) {
        super(abstractDao);
    }
}
