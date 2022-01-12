package ua.com.foxminded.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class GroupService {

    private final Logger logger = LoggerFactory.getLogger(GroupService.class.getName());
    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public Group create(Group group) {

        logger.info("Call groupDao.create()");
        return groupDao.create(group);
    }

    public Group getById(Long id) {

        logger.info("Call groupDao.getById({})", id);
        return groupDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Group with id=" + id + " is not found"));
    }

    public List<Group> getAll() {

        logger.info("Call groupDao.getAll()");
        return groupDao.getAll();
    }

    public void delete(Long id) {

        logger.info("Call groupDao.delete({})", id);
        groupDao.delete(id);
    }
}
